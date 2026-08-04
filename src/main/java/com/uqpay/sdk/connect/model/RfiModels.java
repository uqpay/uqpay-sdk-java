package com.uqpay.sdk.connect.model;

import java.util.List;

public final class RfiModels {
    private RfiModels() {}

    public static final class AnswerItem {
        public String key;
        public String type;
        public List<String> attachments;
    }

    public static final class Question {
        public String key;
        public String comment;
        public String type;
    }

    public static final class RequestItem {
        public Question question;
        public AnswerItem answer;
    }

    public static final class Rfi {
        public String accountId;
        public String rfiId;
        public String status;
        public String createTime;
        public String updateTime;
        public List<RequestItem> request;
    }

    public static final class ListResponse {
        public int totalPages;
        public int totalItems;
        public List<Rfi> data;
    }

    public static final class AnswerRequest {
        public String rfiId;
        public List<AnswerItem> answer;

        public AnswerRequest() {}

        public AnswerRequest(String rfiId, List<AnswerItem> answer) {
            this.rfiId = rfiId;
            this.answer = answer;
        }
    }
}
