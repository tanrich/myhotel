/**
 * Created by tan on 2016/11/25.
 */
require.config({
    baseUrl:"/Hotel/WebApp/js",
    paths:{
        "jquery":"iframe/jquery-2.1.4.min",
        "jqueryForm":"iframe/jquery.form.min",
        "Main-page":"Main-page",
        "Main-body":"Main-body",
        "about":"about",
        "My-hotel":"My-hotel",
        "My-page":"My-page",
        "My-selfInfo":"My-selfInfo",
        "orderHotel":"Order-hotel"
    },
    shim:{
        "jqueryForm":['jquery']
    }
});