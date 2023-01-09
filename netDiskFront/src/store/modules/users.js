
const state = {
    username: "",
    password: ""
}

const actions = {
    //登录
    LoginAction(account, password) {
        account = account.trim()
        password = password.trim()
    }
}

export default {
    state,
    actions
}
