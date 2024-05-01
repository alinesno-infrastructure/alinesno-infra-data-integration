<template>
  <!--
    【作业日志】 功能列表

    @author alinesno ${authorEmail}
    @date 2021-09-23 11:16:52
  -->
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="作业" prop="runJobId">
        <template #default="scope"  >
          <el-select  v-model="queryParams.runJobId" placeholder="作业名称" filterable  clearable size="small"  @keyup.enter.native="handleQuery">
            <el-option  v-for="item in Joblist"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id">
            </el-option>
          </el-select>
        </template>
      </el-form-item>
      <el-form-item label="开始时间" prop="startTime">
        <el-date-picker
          clearable
          size="small"
          v-model="queryParams.startTime"
          range="startTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择执行开始时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="结束时间" prop="endTime">
        <el-date-picker
          clearable
          size="small"
          v-model="queryParams.endTime"
          range="endTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="选择执行结束时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="结果" prop="runStatus">
        <template #default="scope"  >
          <el-select  v-model="queryParams.runStatus" placeholder="结果"   clearable size="small"  @keyup.enter.native="handleQuery">
            <el-option  v-for="item in RunResultList"
                        :key="item.value"
                        :label="item.name"
                        :value="item.value">
            </el-option>
          </el-select>
        </template>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

        <el-row :gutter="10" class="mb8" >
<!--          <el-col :span="1.5">-->
<!--            <el-button-->
<!--              type="primary"-->
<!--              plain-->
<!--              icon="el-icon-plus"-->
<!--              size="mini"-->
<!--              @click="handleAdd"-->
<!--            >新增</el-button>-->
<!--          </el-col>-->
<!--          <el-col :span="1.5">-->
<!--            <el-button-->
<!--              type="success"-->
<!--              plain-->
<!--              icon="Edit"-->
<!--              size="mini"-->
<!--              :disabled="single"-->
<!--              @click="handleUpdate"-->
<!--            >修改</el-button>-->
<!--          </el-col>-->
          <el-col :span="1.5">
            <el-button
              type="danger"
              plain
               icon="Delete"
              size="mini"
              :disabled="multiple"
              @click="handleDelete"
            >删除</el-button>
          </el-col>
<!--          <el-col :span="1.5">-->
<!--            <el-button-->
<!--              type="warning"-->
<!--              plain-->
<!--              icon="el-icon-download"-->
<!--              size="mini"-->
<!--              @click="handleExport"-->
<!--            >导出</el-button>-->
<!--          </el-col>-->

          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>

        </el-row>


    <el-table v-loading="loading" :data="JobRunHisList" @selection-change="handleSelectionChange" :fit="true" sortable>
      <el-table-column type="selection" width="55" align="center" v-if="isEmpty(if_meter)"/>
      <el-table-column label="作业" align="left" prop="runJobIdLabel" />
      <el-table-column label="开始时间" align="center" prop="startTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.startTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束时间" align="center" prop="endTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.endTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="时长(秒)" align="left" prop="scLabel"  />
      <el-table-column label="结果" align="left"   prop="runStatusLabel"  width="50" />
      <el-table-column label="操作"  align="center"  width="180">
        <template #default="scope">
          <el-button
            size="mini"
            type="text"
            icon="Edit"
            @click="handleUpdate(scope.row)"
          >日志详情</el-button>
          <el-button
            size="mini"
            type="text"
             icon="Delete"
            @click="handleDelete(scope.row)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改【作业日志】对话框 -->
    <el-dialog :title="title" v-model="open" width="800px" size="large"  append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
          <el-input v-model="form.remark" :rows="30" type="textarea" placeholder="请输入内容"  readonly />
      </el-form>
    </el-dialog>
  </div>
</template>

<script  setup name="JobRunHis">
import {
  listJobRunHis,
  getJobRunHis,
  delJobRunHis,
  addJobRunHis,
  changeJobRunHisField,
  changeStatusJobRunHis,
  updateJobRunHis,
  exportJobRunHis }      from "@/api/integration/JobRunHis";
import  Condition  from "@/api/search/condition";
import  searchParam  from "@/api/search/searchform";
import {getRunResultList} from "@/api/integration/public";
import {listJob} from "@/api/integration/Job";
import { getDicts } from '@/api/system/dict/data'

import { ref, reactive, onMounted} from 'vue';
import { useRouter, useRoute } from 'vue-router';

const router=useRouter() ;

const route=useRoute()   ;

const { proxy } = getCurrentInstance();


// 总条数
const total = ref(0);

// 弹出层标题
const title = ref( "");

// 选中数组
const names = ref([]);
// 选中数组
const ids = ref([]);

// 状态
const statusOptions = ref([]);

// 【作业日志】表格数据
const JobRunHisList = ref([]);

const RunResultList = ref([]);

// 搜索参数
const searchParams = ref([]);

const Joblist = ref([]);

// 遮罩层
const loading = ref(false);

// 显示搜索条件
const showSearch = ref(true);

// 非单个禁用
const single = ref(true);
// 非多个禁用
const multiple = ref(true);

// 是否显示弹出层
const open = ref(false);

const data = reactive({
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        runJobId: null,
        startTime: null,
        endTime: null,
        runStatus: null,
      },
      // 查询参数配置对象
      queryParamsConfig: {
        runJobId: Condition.eq(),
        startTime: Condition.gtTime(),
        endTime: Condition.leTime(),
        runStatus: Condition.eq(),
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        runJobId: [
          { required: true, message: "作业ID不能为空", trigger: "blur" }
        ],
        startTime: [
          { required: true, message: "执行开始时间不能为空", trigger: "blur" }
        ],
        endTime: [
          { required: true, message: "执行结束时间不能为空", trigger: "blur" }
        ],
        runStatus: [
          { required: true, message: "作业执行结果,0:失败；1:成功不能为空", trigger: "blur" }
        ],
        remark: [
          { required: false, message: "作业执行日志不能为空", trigger: "blur" }
        ]
      }
});

const { queryParams, queryParamsConfig, form, rules } = toRefs(data);

//定义从父页面接收的变量
const props = defineProps({
  if_meter:{  //是否为仪表盘进入界面
    type:String,  //变量类型
    default:''   //变量默认值
  }
});

// 页面加载后触发
onMounted(() => {
  getJoblist();
  getRunResult();
  getList();
})


/** 查询【作业日志】列表 */
function  getList() {
  showSearch.value=isEmpty(props.if_meter.value);
  searchParams.value = searchParam(queryParamsConfig.value, queryParams.value);
  loading.value = true;
  listJobRunHis(searchParams.value).then(response => {
  JobRunHisList.value = response.rows;
  total.value = response.total;
  loading.value = false;
  });
}

function  getJoblist(){
  listJob(queryParams.value).then(response => {
    Joblist.value = response.rows;
  });
}

function  isEmpty(obj){
  if( typeof obj == "undefined" || obj == null || obj == "" || obj == "0" ){
    return true;
  }else{
    return false;
  }
}

function  getRunResult(){
  getRunResultList().then(res => {
    RunResultList.value = res.data;
  });
}

// 取消按钮
function  cancel() {
  open.value = false;
  reset();
}

// 表单重置
function  reset() {
  form.value = {
    runJobId: null,
    startTime: null,
    endTime: null,
    runStatus: null,
    remark: null
  };
  proxy.resetForm("form");
}

/** 搜索按钮操作 */
function  handleQuery() {
  // 获取参数
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function  resetQuery() {
  proxy.resetForm("queryForm");
  handleQuery();
}

// 多选框选中数据
function  handleSelectionChange(selection) {
  ids.value = selection.map(item => item.id)
  names.value = selection.map(item => item.runJobIdLabel + ' ' + item.startTime )
  single.value = selection.length!==1
  multiple.value =  !selection.length
}

/** 新增按钮操作 */
function  handleAdd() {
  reset();
  open.value = true;
  title.value = "添加【作业执行详情】";
}

/** 修改按钮操作 */
function  handleUpdate(row) {
  reset();
  const runJobId = row.id || ids.value
  getJobRunHis(runJobId).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "日志详情";
  });
}

/** 提交按钮 */
function  submitForm() {
  proxy.$refs["form"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateJobRunHis(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addJobRunHis(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
}

/** 删除按钮操作 */
function  handleDelete(row) {
  const runJobIds = row.id || ids.value;
  let nameList = row.runJobIdLabel || names.value;
  //避免弹出窗数据太长，只显示前20条数据
  if ( nameList.length > 20 ) {
    nameList = nameList.slice(0,20);
  }
  proxy.$confirm('是否确认删除【作业日志】名称为"' + nameList + '"的数据项?', "警告", {
      confirmButtonText: "确定",
      cancelButtonText: "取消",
      type: "warning"
    }).then(function() {
      return delJobRunHis(runJobIds);
    }).then(() => {
      getList();
      proxy.$modal.msgSuccess("删除成功");
    }).catch(error => {

    })
}

/** 状态修改 **/
function  handleStatusChange(row) {
  return changeStatusJobRunHis(row.id, row.status).then(response=>{
    if( response.code == 200 ){
      proxy.$modal.msgSuccess("操作成功");
    }
  });
}

/** 修改字段状态 **/
function  chanageFile(value , filed , id){
  return changeJobRunHisField(value , filed , id).then(response =>{
    if( response.code == 200 ){
      proxy.$modal.msgSuccess("操作成功");
    }
  }) ;
}

/** 导出按钮操作 */
function  handleExport() {
  const queryParams = queryParams.value;
  proxy.$confirm('是否确认导出所有【作业日志】数据项?', "警告", {
      confirmButtonText: "确定",
      cancelButtonText: "取消",
      type: "warning"
    }).then(function() {
      return exportJobRunHis(queryParams);
    }).then(response => {
      download(response.msg);
    })
}

</script>
