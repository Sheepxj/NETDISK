
const state ={
    Authorization: localStorage.getItem('Authorization') ? localStorage.getItem('Authorization') : ''
}

const mutations= {
    // 修改token，并将token存入localStorage
    changeLogin (user) {
        // 修改vuex中的token
        state.Authorization = user.Authorization;
        // 修改localStorage中的token
        localStorage.setItem('Authorization', user.Authorization);
    }
}

export default {
    state,
    mutations
}
