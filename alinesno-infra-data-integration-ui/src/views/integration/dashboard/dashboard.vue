<template>
  <!--
    dashboard
    @author paul
    @version 1.0.0
  -->
  <div class="app-container">
    <el-row :gutter="24">
      <el-col :span="8">
        <el-card class="box-card">
          <el-row>
            <el-col :span="10">
              <div class="col-sm-4" style="opacity: 0.2">
                <i class="fa fa-globe fa-5x" aria-hidden="true"></i>
              </div>
            </el-col>
            <el-col :span="12">
              <div class="grid-content bg-purple-light">
                总任务数: 2
              </div>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="box-card">
          <el-row>
            <el-col :span="10">
              <div class="col-sm-4" style="opacity: 0.2">
                <i class="fa fa-globe fa-5x" aria-hidden="true"></i>
              </div>
            </el-col>
            <el-col :span="12">
              <div class="grid-content bg-purple-light">
                总作业数: 2
              </div>
              <div class="grid-content bg-purple-light">

              </div>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="box-card">
          <el-row>
            <el-col :span="10">
              <div class="col-sm-4" style="opacity: 0.2">
                <i class="fa fa-globe fa-5x" aria-hidden="true"></i>
              </div>
            </el-col>
            <el-col :span="12">
              <div class="grid-content bg-purple-light">
                总监控任务数: 2
              </div>
              <div class="grid-content bg-purple-light">
<!--                <iframe src="D:\alinesno-cloud-data-etl-management\alinesno-cloud-data-etl-ui\src\views\etl\jobMonitor\list.vue" id="mobsf" scrolling="no" frameborder="0" style="position:absolute;top:64px;left: 240px;right:0px;bottom:100px;">123qwe</iframe>-->
              </div>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
    </el-row>
<br>
    <el-row :gutter="20">
      <el-col :span="12"><div class="grid-content bg-purple">转换监控记录</div></el-col>
      <el-col :span="12"><div class="grid-content bg-purple">作业监控记录</div></el-col>
    </el-row>

  </div>

</template>

<script  setup name="dashboard">


import { useRouter, useRoute } from 'vue-router';

const router=useRouter() ;

const route=useRoute()   ;

const { proxy } = getCurrentInstance();

// 页面加载后触发
onMounted(() => {
  // getList();
})


</script>

