import CONFIG from './config'

const BASE_URL = (CONFIG ? CONFIG.userUrl : "");
const USER_TOKEN = "user.token";

export type ContactInfo = {
    firstName: string,
    lastName: string,
    email: string,
    phone: string, // to accomodate +420...
}

export type UserInfo = ContactInfo & {
    username: string,
}

export function login(username: string, password: string): Promise<void> {
    var form = new URLSearchParams()
    form.append("username", username)
    form.append("password", password)
    return fetch(BASE_URL + "/login", {
        method: 'POST',
        headers: {
            'content-type': 'application/x-www-form-urlencoded'
        },
        body: form
    }).then(res => {
        if (res.status === 200) {
            return res.text().then(token => sessionStorage.setItem(USER_TOKEN, token));
        } else {
            throw new Error("Login failure")
        }
    })
}

export function logout() {
    const token = sessionStorage.getItem(USER_TOKEN)
    if (token) {
        return fetch(BASE_URL + "/logout", {
            method: 'POST',
            body: token,
        }).finally(() => sessionStorage.removeItem(USER_TOKEN))
    } else {
        return Promise.resolve()
    }
}

export function getUserToken(): string | undefined {
    const token = sessionStorage.getItem(USER_TOKEN)
    return token === null ? undefined : token;
}

export function fetchUserInfo(token: string): Promise<UserInfo> {
    return fetch(BASE_URL + "/info?token=" + encodeURIComponent(token)).then(res => res.json())
}