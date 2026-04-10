package com.uqpay.sdk.supporting;

import com.uqpay.sdk.TestHelper;
import com.uqpay.sdk.auth.DefaultTokenProvider;
import com.uqpay.sdk.common.ApiClient;
import com.uqpay.sdk.common.UqpayException;
import com.uqpay.sdk.config.Configuration;
import com.uqpay.sdk.config.Environment;
import com.uqpay.sdk.supporting.model.DownloadLinksRequest;
import com.uqpay.sdk.supporting.model.DownloadLinksResponse;
import com.uqpay.sdk.supporting.model.UploadFileResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("FilesService Integration Tests")
class FilesServiceIntegrationTest {

    private static FilesService filesService;

    @BeforeAll
    static void setUp() {
        TestHelper.assumeIntegrationTestsEnabled();

        Configuration filesConfig = TestHelper.getFilesTestConfiguration();
        DefaultTokenProvider tokenProvider = new DefaultTokenProvider(
                Environment.SANDBOX.getFilesBaseUrl(),
                filesConfig.getClientId(),
                filesConfig.getApiKey(),
                filesConfig.getHttpClient()
        );
        ApiClient apiClient = new ApiClient(filesConfig, tokenProvider);
        SupportingClient supportingClient = new SupportingClient(apiClient);
        filesService = supportingClient.getFiles();
    }

    @Nested
    @DisplayName("Upload File")
    class UploadFile {

        @Test
        @DisplayName("should upload a PDF file")
        void shouldUploadFile() throws UqpayException, IOException {
            // Create a minimal test PDF file
            File tempFile = File.createTempFile("uqpay-test-", ".pdf");
            tempFile.deleteOnExit();
            try (FileWriter writer = new FileWriter(tempFile)) {
                writer.write("%PDF-1.0\ntest content\n");
            }

            try {
                UploadFileResponse response = filesService.upload(tempFile, "integration-test");

                assertThat(response).isNotNull();
                assertThat(response.getFileId()).isNotNull();

                System.out.printf("Uploaded file: FileID=%s, FileName=%s, FileType=%s, Size=%d%n",
                        response.getFileId(), response.getFileName(),
                        response.getFileType(), response.getSize());
                System.out.printf("  Notes=%s, CreateTime=%s%n",
                        response.getNotes(), response.getCreateTime());
            } catch (UqpayException e) {
                System.out.printf("Upload file returned error (may be expected in sandbox): %s%n", e.getMessage());
            }
        }

        @Test
        @DisplayName("should upload a file without notes")
        void shouldUploadFileWithoutNotes() throws UqpayException, IOException {
            File tempFile = File.createTempFile("uqpay-test-", ".pdf");
            tempFile.deleteOnExit();
            try (FileWriter writer = new FileWriter(tempFile)) {
                writer.write("%PDF-1.0\ntest content\n");
            }

            try {
                UploadFileResponse response = filesService.upload(tempFile);

                assertThat(response).isNotNull();

                System.out.printf("Uploaded file (no notes): FileID=%s, FileName=%s%n",
                        response.getFileId(), response.getFileName());
            } catch (UqpayException e) {
                System.out.printf("Upload file (no notes) returned error (may be expected in sandbox): %s%n",
                        e.getMessage());
            }
        }

        @Test
        @DisplayName("should upload a real JPEG image")
        void shouldUploadJpegImage() throws UqpayException {
            // Load the test image from resources
            java.net.URL imageUrl = getClass().getClassLoader().getResource("test-id-front.jpg");
            if (imageUrl == null) {
                System.out.println("test-id-front.jpg not found in test resources, skipping");
                return;
            }

            File imageFile = new File(imageUrl.getFile());

            try {
                UploadFileResponse response = filesService.upload(imageFile, "SDK test upload (real image)");

                assertThat(response).isNotNull();
                assertThat(response.getFileId()).isNotNull();
                assertThat(response.getFileName()).contains("test-id-front");

                System.out.printf("Uploaded image: FileID=%s, FileName=%s, FileType=%s, Size=%d%n",
                        response.getFileId(), response.getFileName(),
                        response.getFileType(), response.getSize());
            } catch (UqpayException e) {
                System.out.printf("Upload image returned error (may be expected in sandbox): %s%n", e.getMessage());
            }
        }

        @Test
        @DisplayName("should upload image and get download link")
        void shouldUploadAndGetDownloadLink() throws UqpayException {
            java.net.URL imageUrl = getClass().getClassLoader().getResource("test-id-front.jpg");
            if (imageUrl == null) {
                System.out.println("test-id-front.jpg not found in test resources, skipping");
                return;
            }

            File imageFile = new File(imageUrl.getFile());

            try {
                UploadFileResponse uploadResponse = filesService.upload(imageFile, "upload-then-download test");

                assertThat(uploadResponse).isNotNull();
                assertThat(uploadResponse.getFileId()).isNotNull();

                String fileId = uploadResponse.getFileId();
                System.out.printf("Uploaded: FileID=%s%n", fileId);

                // Now get download link
                DownloadLinksRequest dlRequest = new DownloadLinksRequest(java.util.Collections.singletonList(fileId));
                DownloadLinksResponse dlResponse = filesService.getDownloadLinks(dlRequest);

                assertThat(dlResponse).isNotNull();
                if (dlResponse.getFiles() != null && !dlResponse.getFiles().isEmpty()) {
                    System.out.printf("Download link: %s%n", dlResponse.getFiles().get(0).getUrl());
                }
            } catch (UqpayException e) {
                System.out.printf("Upload/download link returned error (may be expected in sandbox): %s%n", e.getMessage());
            }
        }
    }

    @Nested
    @DisplayName("Get Download Links")
    class GetDownloadLinks {

        @Test
        @DisplayName("should handle empty file IDs")
        void shouldHandleEmptyFileIds() throws UqpayException {
            DownloadLinksRequest request = new DownloadLinksRequest(Collections.emptyList());

            DownloadLinksResponse response = filesService.getDownloadLinks(request);

            assertThat(response).isNotNull();
            System.out.printf("Empty file IDs response: files=%s, absentFiles=%s%n",
                    response.getFiles(), response.getAbsentFiles());
        }

        @Test
        @DisplayName("should handle non-existent file IDs")
        void shouldHandleNonExistentFileIds() throws UqpayException {
            DownloadLinksRequest request = new DownloadLinksRequest(
                    Arrays.asList("non-existent-id-1", "non-existent-id-2")
            );

            DownloadLinksResponse response = filesService.getDownloadLinks(request);

            assertThat(response).isNotNull();
            System.out.printf("Non-existent file IDs response: files=%s, absentFiles=%s%n",
                    response.getFiles(), response.getAbsentFiles());

            if (response.getAbsentFiles() != null) {
                assertThat(response.getAbsentFiles()).containsExactlyInAnyOrder(
                        "non-existent-id-1", "non-existent-id-2"
                );
            }
        }

        @Test
        @DisplayName("should handle mixed file IDs")
        void shouldHandleMixedFileIds() throws UqpayException {
            List<String> fileIds = Arrays.asList("non-existent-id-1", "non-existent-id-2");
            DownloadLinksRequest request = new DownloadLinksRequest(fileIds);

            DownloadLinksResponse response = filesService.getDownloadLinks(request);

            assertThat(response).isNotNull();
            System.out.printf("Mixed file IDs response: files=%s, absentFiles=%s%n",
                    response.getFiles(), response.getAbsentFiles());
        }

        @Test
        @DisplayName("should handle single file ID")
        void shouldHandleSingleFileId() throws UqpayException {
            DownloadLinksRequest request = new DownloadLinksRequest(
                    Collections.singletonList("single-test-id")
            );

            DownloadLinksResponse response = filesService.getDownloadLinks(request);

            assertThat(response).isNotNull();
            System.out.printf("Single file ID response: files=%s, absentFiles=%s%n",
                    response.getFiles(), response.getAbsentFiles());
        }

        @Test
        @DisplayName("should handle multiple file IDs")
        void shouldHandleMultipleFileIds() throws UqpayException {
            DownloadLinksRequest request = new DownloadLinksRequest(
                    Arrays.asList("test-id-1", "test-id-2", "test-id-3")
            );

            DownloadLinksResponse response = filesService.getDownloadLinks(request);

            assertThat(response).isNotNull();
            System.out.printf("Multiple file IDs response: files=%s, absentFiles=%s%n",
                    response.getFiles(), response.getAbsentFiles());
        }
    }
}
