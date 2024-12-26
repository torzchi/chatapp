package com.example.chatapp.model;

public class ConversationRequest {

    private String currentUser;
    private String selectedUser;

    // Getters and setters
    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    public String getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(String selectedUser) {
        this.selectedUser = selectedUser;
    }
}
