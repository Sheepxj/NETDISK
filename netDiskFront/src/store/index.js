import { createStore } from "vuex";
import users from './modules/users'
import token from './modules/token'

const store = createStore({
    modules: {
        users,
        token
    }
})
