import { createRouter, createWebHashHistory } from "vue-router";

const allRouter = [
    {
        path: "/",
        //定向到登录界面
        redirect: "/login"
    },
    {
        path: "/login",
        name: "login",
        component: () => import("@/views/login/index.vue"),
    },
    {
        path: "/signup",
        name: "signup",
        component: () => import("@/views/signUp/index.vue"),
    },
    {
        path: "/dashboard",
        name: "dashboard",
        component: () => import("@/views/home/index.vue"),
        children: [
            {
                path: "/dashboard/main",
                name: "main",
                component: () => import("@/components/main/index.vue")
            }
        ]
    }
]

const router= createRouter({
    history: createWebHashHistory(),
    routes: allRouter,

})

router.beforeEach((to, from, next) => {
    // 访问的是登录页，直接放行
    if (to.path === '/login') {
        next();
    } else {  // 访问的并不是登录页，如果没有token,就跳转到登录页；如果有token，直接放行
        let token = localStorage.getItem('token');

        if (token === null || token === '') {
            next('/login');
        } else {
            next();
        }
    }
});

export {router}
