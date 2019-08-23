app.controller('indexController',function ($scope,$controller,loginService) {

    $scope.showLoginName=function () {
        loginService.loginName().success(
            function (resopnse) {
                $scope.loginName=resopnse.loginName;
            }
        )
        
    }

});