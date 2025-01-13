<template>
  <!--
    【作业】 功能列表
    @author paul
    @version 1.0.0
  -->
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px" @submit.native.prevent>

      <el-form-item label="作业名称" prop="name">
        <el-input
          v-model="queryParams.name"
          ref="queryParams.name"
          placeholder="请输入作业名称"
          clearable
          size="small"
          wrapper="like"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
           icon="Delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
        >删除
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="List" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="作业名称" align="left" prop="name"/>
      <el-table-column label="任务分类" align="left" prop="categoryName"/>
      <el-table-column label="作业描述" width="350" align="left" :show-overflow-tooltip="true" prop="description"/>
<!--      <el-table-column label="作业类型" align="left" prop="type"/>-->
      <el-table-column label="日志级别" align="left" prop="logLevel"  width="160"/>
      <el-table-column label="作业状态" align="center" prop="statusLabel" width="90"/>
      <el-table-column label="最后更新时间" align="left" prop="updateTime" width="160">
        <template #default="scope">
          <span>{{ scope.row.updateTime|| scope.row.addTime| timefilters }}</span>  <!-- 时间显示异常 0-0-0-->
        </template>
      </el-table-column>
      <el-table-column label="操作" width="280" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button
            v-show = "scope.row.status == 2"
            size="mini"
            type="text"
            icon="VideoPlay"
            @click="handleRunF(scope.row.id)"
          >运行
          </el-button>
          <el-button
            v-show = "scope.row.status == 1"
            size="mini"
            type="text"
            icon="VideoPause"
            @click="handleStopF(scope.row.id)"
          >停止
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="Edit"
            @click="handleUpdate(scope.row)"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
             icon="Delete"
            @click="handleDelete(scope.row)"
          >删除
          </el-button>
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

    <!-- 添加或修改【作业】对话框 -->
    <el-dialog :title="title" v-model="open" width="800px" append-to-body>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="执行方式" prop="type">
          <el-select v-model="form.type" placeholder="请输入作业类型" style="width:680px">
            <el-option
              v-for="item in RunTypeList"
              :key="item.value"
              :label="item.name"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="仓库名称" prop="gitId">
          <el-select v-model="form.gitId" placeholder="请输入执行方式" style="width:680px"  >
            <el-option
              v-for="item in gitList"
              :key="item.id"
              :label="item.gitName"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="文件路径" prop="relativeLocation">
          <el-input v-model="form.relativeLocation"  maxlength="255" show-word-limit placeholder="请输入作业文件在仓库中的完整路径,如:acplog/ods_classify.kjb" style="width:680px" />
        </el-form-item>

        <el-form-item label="作业名称" prop="name">
          <el-input v-model="form.name" maxlength="255" show-word-limit placeholder="请输入作业名称,如:ods_classify.kjb"/>
        </el-form-item>
        <el-form-item label="任务分类" prop="categoryId">
          <el-select v-model="form.categoryId" placeholder="请选择任务分类" style="width:680px">
            <el-option
              v-for="item in CategoryList"
              :key="item.id"
              :label="item.categoryName"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="转换任务" prop="transIds">
          <el-select v-model="form.transIds" multiple placeholder="请选择转换任务" style="width:680px" filterable >
            <el-option
              v-for="item in transList"
              :key="item.id"
              :label="item.name"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="定时策略" prop="quartz">
          <el-select v-model="form.quartz" placeholder="请输入定时策略" style="width:680px">
            <el-option
              v-for="item in QuartzList"
              :key="item.id"
              :label="item.quartzDescription"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="日志级别" prop="transLogLevel">
          <el-select v-model="form.logLevel" placeholder="请输入日志级别" style="width:680px">
            <el-option
              v-for="item in LogLevelList"
              :key="item.value"
              :label="item.name"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="作业描述" prop="description">
          <el-input v-model="form.description" type="textarea" maxlength="255" show-word-limit :autosize="{minRows:4}" placeholder="请输入作业描述"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script   setup name="Job">
import {addJob, delJob, exportJob, getJob, handleRun, listJob, updateJob, handleStop} from "@/api/integration/Job";
import { listCategory } from "@/api/integration/Category";
import {getLogLevelList, getRunTypeList} from "@/api/integration/public";
import {changeFile} from "../../../api/integration/Job";
import {getStatusList} from "../../../api/integration/public";
import  Condition  from "@/api/search/condition";
import  searchParam  from "@/api/search/searchform";
import {listTrans} from "@/api/integration/Trans";
import {listQuartz} from "@/api/integration/Quartz";
import {listBuildGit} from "@/api/integration/buildGit.js";
import { getDicts } from '@/api/system/dict/data'

import { ref, reactive, onMounted} from 'vue';
import { useRouter, useRoute } from 'vue-router';

const router=useRouter() ;

const route=useRoute()   ;

const { proxy } = getCurrentInstance();

// 总条数
const total = ref(0);

// 弹出层标题
const title = ref("");

// 是否显示弹出层
const open = ref(false);

// 非单个禁用
const single = ref(true);

// 非多个禁用
const multiple = ref(true);

// 显示搜索条件
const showSearch = ref(true);

// 遮罩层
const loading = ref(true);

// 选中数组
const names = ref([]);

// 选中数组
const ids = ref([]);

const statusOptions = ref([]);

// 【作业】表格数据
const List = ref([]);

// 任务分类列表
const transList = ref([]);

// 任务分类列表
const CategoryList = ref([]);

// 转换类型列表
const RunTypeList = ref([]);

// 日志类型列表
const LogLevelList = ref([]);

//获取定时策略列表
const QuartzList = ref([]);

//获取任务状态列表
const statusList = ref([]);

// 搜索参数
const searchParams = ref([]);

const searchParamTem = ref([]);

const gitList = ref([]);

const projectList = ref([]);

const data = reactive({
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        categoryId: null,
        name: null,
        description: null,
        type: null,
        path: null,
        jobRepositoryId: null,
        quartz: null,
        syncStrategy: null,
        logLevel: null,
        status: null
      },
      // 查询参数配置对象
      queryParamsConfig: {
        name: Condition.like()
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        categoryId: [
          {required: true, message: "分类ID不能为空", trigger: "blur"}
        ],
        name: [
          {required: true, message: "作业名称不能为空", trigger: "blur"}
        ],
        type: [
          {required: true, message: "作业类型不能为空", trigger: "blur"}
        ],
        path: [
          {required: true, message: "作业路径不能为空", trigger: "blur"}
        ],
        jobRepositoryId: [
          {required: true, message: "仓库ID不能为空", trigger: "blur"}
        ],
        quartz: [
          {required: true, message: "任务调度不能为空", trigger: "blur"}
        ],
        syncStrategy: [
          {required: true, message: "同步策略不能为空", trigger: "blur"}
        ],
        logLevel: [
          {required: true, message: "日志级别不能为空", trigger: "blur"}
        ],
        status: [
          {required: true, message: "作业状态不能为空", trigger: "blur"}
        ],
        gitId: [
          {required: true, message: "仓库名称不能为空", trigger: "blur"}
        ],
        relativeLocation: [
          {required: true, message: "转换任务的仓库路径不能为空", trigger: "blur"}
        ]
      },
      Params: {
        pageNum: 1,
        pageSize: 1000,
        hasStatus: 0
      },
      ParamsConfig: {
      }

});

const { queryParams, queryParamsConfig, form, rules, Params, ParamsConfig } = toRefs(data);

// 页面加载后触发
onMounted(() => {
  //获取任务分类
  getCategoryList();

  //获取定时策略
  getQuartzList();

  //获取运行类型
  getRunType();

  //获取日志级别类型
  getLogLevel();

  //获取转换任务类型
  getTransList();

  //获取任务状态类型
  getstatusList();

  //获取仓库清单
  getGitList();

  getList();
})


/**
 * 获取仓库列表
 */
function  getGitList() {
  searchParamTem.value = searchParam(ParamsConfig.value, Params.value);
  listBuildGit(searchParamTem.value).then(res => {
    gitList.value = res.rows;
  });
}

/** 查询【作业】列表 */
// function  getList() {
//   loading.value = true;
//   searchParams.value = searchParam(queryParamsConfig.value, queryParams.value);
//     listJob(searchParams.value).then(response => {
//     List.value = response.rows;
//     total.value = response.total;
//     loading.value = false;
//   });
// }

function  getList() {
  loading.value = true;
  // searchParams.value = searchParam(queryParamsConfig.value, queryParams.value);
  listJob(queryParams.value).then(response => {
    List.value = response.rows;
    total.value = response.total;
    loading.value = false;
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
    id: null,
    categoryId: null,
    name: null,
    description: null,
    type: null,
    path: null,
    jobRepositoryId: null,
    quartz: null,
    syncStrategy: null,
    transIds: null,
    fileId: null,
    logLevel: null,
    status: null
  };
  proxy.resetForm("formRef");
}

/**
 * 修改字段状态
 */
// function  changeFile(value, field, id) {
//   console.log('value = ' + value + ' , field = ' + field + ' , id = ' + id);
//   const data = {
//     value,
//     field,
//     id
//   }
//   return changeFile(data).then(res => {
//     res.code === 200 ? proxy.$modal.msgSuccess(res.msg) : proxy.$modal.msgError(res.msg);
//   });
// }

function  getTransList() {
  searchParamTem.value = searchParam(ParamsConfig.value, Params.value);
  listTrans(searchParamTem.value).then(res => {
    transList.value = res.rows;
  });
}

/**
 * 获取任务分类列表
 */
function  getCategoryList() {
  searchParamTem.value = searchParam(ParamsConfig.value, Params.value);
  listCategory(searchParamTem.value).then(res => {
    CategoryList.value = res.rows;
  });
}

/**
 * 获取运行策略
 */
function  getQuartzList() {
  searchParamTem.value = searchParam(ParamsConfig.value, Params.value);
  listQuartz(searchParamTem.value).then(response => {
    QuartzList.value = response.rows;
  });
}

/**
 * 获取运行类型
 */
function  getRunType() {
  getRunTypeList().then(res => {
    RunTypeList.value = res.data;
  });
}

/**
 * 获取日志级别类型
 */
function  getLogLevel() {
  getLogLevelList().then(res => {
    LogLevelList.value = res.data;
  });
}

/**
 * 获取日志级别类型
 */
function  getstatusList() {
  getStatusList().then(res => {
    statusList.value = res.data;
  });
}

/**
 * 上传成功回调方法
 */
function  handleUploadFile(param) {
  const fileObj = param.file;
  // FormData 对象
  const form = new FormData();
  // 文件对象
  form.append("file", fileObj);
  form.append("appCode", process.env.VUE_APP_STORAGE_APPCODE);
  request({
    url: process.env.VUE_APP_STORAGE_API + 'upload',
    method: 'post',
    headers: {
      'Content-Type': 'multipart/form-data'
    },
    data: form
  }).then(res => {
    if (res.code === 200) {
      form.value.fileId = res.data.id;
      form.value.name = fileObj.name;
      proxy.$modal.msgSuccess(res.message);
    } else {
      proxy.$modal.msgError(res.message);
    }
  });
}

/** 搜索按钮操作 */
function  handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function  resetQuery() {
  proxy.resetForm("queryForm");
  handleQuery();
}

/**
 *运行转换任务
 */
function  handleRunF(id) {
  handleRun(id).then(res => {
    res.code === 200 ? proxy.$modal.msgSuccess(res.msg) : proxy.$modal.msgError(res.msg);
    getList();
  })
}

// 多选框选中数据
function  handleSelectionChange(selection) {
  ids.value = selection.map(item => item.id)
  names.value = selection.map(item => item.name  + ' ')
  single.value = selection.length !== 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function  handleAdd() {
  reset();
  form.value.type='file';
  open.value = true;
  title.value = "添加【作业】";
}

/** 修改按钮操作 */
function  handleUpdate(row) {
  reset();
  const id = row.id || ids.value ;
  getJob(id).then(response => {
    debugger
    form.value = response.data;
    console.log("修改任务信息：" + form.value ) ;
    open.value = true;
    title.value = "修改【作业】";
  });
}

/** 提交按钮 */
function  submitForm() {
  debugger
  proxy.$refs["formRef"].validate(valid => {
    if (valid) {
      debugger
      console.log("作业任务id:" + form.value.id ) ;
      if ( form.value.id != null  ) {
        debugger
        updateJob(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        debugger
        addJob(form.value).then(response => {
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
  const id = row.id || ids.value;
  let nameList = row.name || names.value;
  //避免弹出窗数据太长，只显示前15条数据
  if ( nameList.length > 15 ) {
    nameList = nameList.slice(0,15);
  }
  proxy.$confirm('是否确认删除【作业】名称为"' + nameList + '"的数据项?', "警告", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning"
  }).then(function () {
    return delJob(id);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(error => {

  })
}

/** 导出按钮操作 */
function  handleExport() {
  const queryParams = queryParams.value;
  proxy.$confirm('是否确认导出所有【作业】数据项?', "警告", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning"
  }).then(function () {
    return exportJob(queryParams);
  }).then(response => {
    download(response.msg);
  })
}

//转换任务过滤条件
// function  transFilter(val){
//   this.value = val
//   if (val) {
//     this.options = this.optionsCopy.filter((item) => {
//       return item.label.indexOf(val)>-1 || item.value.indexOf(val)>-1
//     })
//   } else {
//     this.options = this.options2;
//   }
// }

/**
 *停止作业任务
 */
function  handleStopF(id) {
  handleStop(id).then(res => {
    res.code === 200 ? proxy.$modal.msgSuccess(res.msg) : proxy.$modal.msgError(res.msg);
    getList();
  })
}

//   },
//   filters: {
//     timefilters(val) {
//       if (val === null || val === '') {
//         return '暂无时间';
//       }
//       const d = new Date(val); // val 为表格内取到的后台时间
//       const month = d.getMonth() + 1 < 10 ? `0${d.getMonth() + 1}` : d.getMonth() + 1;
//       const day = d.getDate() < 10 ? `0${d.getDate()}` : d.getDate();
//       const hours = d.getHours() < 10 ? `0${d.getHours()}` : d.getHours();
//       const min = d.getMinutes() < 10 ? `0${d.getMinutes()}` : d.getMinutes();
//       const sec = d.getSeconds() < 10 ? `0${d.getSeconds()}` : d.getSeconds();
//       const times = `${d.getFullYear()}-${month}-${day} ${hours}:${min}:${sec}`;
//       return times;
//     }
//   }
// };
</script>
