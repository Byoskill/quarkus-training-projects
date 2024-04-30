const accessTokenLabel = document.getElementById('access-token');
const refreshTokenLabel = document.getElementById('refresh-token');
const refreshTokenButton = document.getElementById('refresh-token-button');
const testAuthButton = document.getElementById('test-auth-button');

function refreshToken() {
    console.log('Refreshing token');
    fetch('/login/refresh', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + refreshTokenLabel.value
        }
    })
        .then(response => response.json())
        .then(data => {
            console.log(data);
            accessTokenLabel.innerText = data.access_token;
            refreshTokenLabel.innerText = data.refresh_token;
        });
}

function authToken() {
    console.log('Testing authentication');
    fetch('/login/test', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + accessTokenLabel.value
        }
    }).then(response => {
        if (response.status === 200) {
            alert('Authentication successful');
        } else {
            alert('Authentication failed');
        }
    });
}

refreshTokenButton.addEventListener('click', refreshToken);
testAuthButton.addEventListener('click', authToken);