const loginForm = document.getElementById('login-form');
const accessToken = document.getElementById('access-token');
const refreshToken = document.getElementById('refresh-token');
const refreshTokenButton = document.getElementById('refresh-token-button');
const testAuthButton = document.getElementById('test-auth-button');

function refreshToken() {
    console.log('Refreshing token');
    fetch('/login/refresh', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + refreshToken.innerText
        }
    })
        .then(response => response.json())
        .then(data => {
            console.log(data);
            accessToken.innerText = data.access_token;
            refreshToken.innerText = data.refresh_token;
        });
}

function authToken() {
    console.log('Testing authentication');
    fetch('/login/auth', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + refreshToken.innerText
        }
    }).then(response => {
        if (response.status === 200) {
            alert('Authentication successful');
        } else {
            alert('Authentication failed');
        }
    });
}
