app.controller('baseController',function ($scope) {

    //分页控间配置
    $scope.paginationConf= {
        currentPage: 1,
        totalItems: 10,
        itemsPerPage: 10,
        perPageOptions: [10, 20, 30, 40, 50],
        onChange: function () {
            $scope.reloadList();//重新加载
        }
    }
    $scope.reloadList = function(){
        //切换页码
        //$scope.findPage( $scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
        $scope.search( $scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
    }


    //选中的ID集合
    $scope.selectIds=[];
    //更新复选款
    $scope.updateSelection=function($event,id){
        if($event.target.checked){
            //选中添加到数组中
            $scope.selectIds.push(id);
        }else {
            //未选中删除
            var idx=  $scope.selectIds.indexOf(id);
            $scope.selectIds.splice(idx,1);
        }
    }
})