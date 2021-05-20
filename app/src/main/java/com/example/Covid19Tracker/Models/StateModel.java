package com.example.Covid19Tracker.Models;

public class StateModel {
        private String state;
        private String confirmed;
        private String active;
        private String deceased;
        private String recovered;

        public StateModel(String state, String confirmed, String active, String deceased,
                          String recovered) {
            this.state = state;
            this.confirmed = confirmed;
            this.active = active;
            this.deceased = deceased;
            this.recovered = recovered;
        }

        public String getState() {
            return state;
        }

        public String getConfirmed() {
            return confirmed;
        }

        public String getActive() {
            return active;
        }

        public String getDeceased() {
            return deceased;
        }

        public String getRecovered() {
            return recovered;
        }

    }


