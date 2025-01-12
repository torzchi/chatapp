let allUsers = []; // Store all users

async function showCreateChatModal() {
    const modal = document.getElementById('createChatModal');
    const userSelectList = document.getElementById('userSelectList');
    const searchInput = document.getElementById('userSearchInput');
    userSelectList.innerHTML = '';

    try {
        const response = await fetch('/api/users');
        allUsers = await response.json(); // Store all users

        // Display all users initially
        displayFilteredUsers(allUsers);

        // Add search input event listener
        searchInput.value = ''; // Clear previous search
        searchInput.addEventListener('input', handleSearch);

        modal.style.display = 'block';
    } catch (error) {
        console.error('Error fetching users:', error);
    }
}

function displayFilteredUsers(users) {
    const userSelectList = document.getElementById('userSelectList');
    userSelectList.innerHTML = ''; // Clear current list

    users.forEach(user => {
        if (user.id !== currentUserId) {
            const div = document.createElement('div');
            div.className = 'user-select-item';
            div.innerHTML = `
                <input type="checkbox" id="user-${user.id}" value="${user.id}">
                <label for="user-${user.id}">${user.username}</label>
            `;
            userSelectList.appendChild(div);
        }
    });
}

function handleSearch(event) {
    const searchTerm = event.target.value.toLowerCase();
    const filteredUsers = allUsers.filter(user =>
        user.username.toLowerCase().includes(searchTerm)
    );
    displayFilteredUsers(filteredUsers);
}



function closeModal() {
    document.getElementById('createChatModal').style.display = 'none';
    // Remove search event listener when closing modal
    document.getElementById('userSearchInput').removeEventListener('input', handleSearch);
}
