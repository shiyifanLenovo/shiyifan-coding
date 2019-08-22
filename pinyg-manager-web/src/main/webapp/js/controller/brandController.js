app.controller('brandController' ,function($scope,$controller,brandService){
    //继承
    $controller('baseController',{$scope:$scope})

    //读取列表数据绑定到表单中
    $scope.findAll=function(){
        brandService.findAll().success(
            function(response){
                $scope.list=response;
            }
        );
    }

    //分页
    $scope.findPage = function(page,rows) {
        brandService.findPage(page,rows).success(
            function (response) {
                $scope.list=response.rows;
                //更新总记录数
                $scope.paginationConf.totalItems=response.total;
            }

        )
    }
    //新增
    $scope.save=function () {

        var Object=null;
        if($scope.entity.id!=null){
            Object=brandService.update($scope.entity);
        }else{
            Object=brandService.add($scope.entity);
        }
        Object.success(
            function (response) {
                if(response.success){
                    $scope.reloadList();
                }else {
                    alert(response.message)
                }

            }

        )
    }
    //查询单个实体
    $scope.findOne=function (id) {
        brandService.findOne(id).success(
            function (response) {
                $scope.entity=response;
            }
        )
    }


    //删除
    $scope.delete=function () {
        brandService.delete($scope.selectIds).success(
            function (resopnse) {
                if(resopnse.success){
                    $scope.reloadList();
                }else {
                    alert(resopnse.message)
                }
            }
        )

    }
    //定义搜素对象查询
    $scope.searchEntity={};
    $scope.search=function(page,rows){
        brandService.search(page,rows,$scope.searchEntity).success(
            function(response){
                $scope.paginationConf.totalItems=response.total;//总记录数
                $scope.list=response.rows;//给列表变量赋值
            }
        );
    }


});