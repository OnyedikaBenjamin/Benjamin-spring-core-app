package org.security.securityErrors;

import java.time.LocalDateTime;

public class SecurityErrorResponse {

        private LocalDateTime timestamp;
        private int status;
        private String error;
        private String message;
        private String path;

        public SecurityErrorResponse(
                LocalDateTime timestamp,
                int status,
                String error,
                String path
        ) {
            this.timestamp = LocalDateTime.now();
            this.status = status;
            this.error = error;
            this.message = message;
            this.path = path;
        }

        public LocalDateTime getTimestamp() {
            return timestamp;
        }

        public int getStatus() {
            return status;
        }

        public String getError() {
            return error;
        }

        public String getMessage() {
            return message;
        }

        public String getPath() {
            return path;
        }
}
