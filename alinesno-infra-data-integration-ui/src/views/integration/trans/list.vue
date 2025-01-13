<template>
  <!--
    【转换】 功能列表
    @author paul
    @version 1.0.0
  -->
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px"  @submit.native.prevent>

      <el-form-item label="转换名称" prop="name">
        <el-input
          v-model="queryParams.name"
          ref="searchForm.name"
          placeholder="请输入转换名称"
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
      <el-table-column label="转换名称" align="left" prop="name" :show-overflow-tooltip="true"/>
      <el-table-column label="任务分类" align="left" prop="categoryName"/>
      <el-table-column label="转换描述" align="left" prop="description" :show-overflow-tooltip="true"/>
<!--      <el-table-column label="转换类型" align="left" prop="type"  width="120"/>-->
      <el-table-column label="日志级别" align="left" prop="logLevel"  width="160"/>
      <el-table-column label="转换状态" align="center" prop="statusLabel" width="90"/>
      <el-table-column label="最后更新时间" align="left" prop="updateTime" width="160">
        <template #default="scope">
          <span>{{  scope.row.updateTime || scope.row.addTime   }}</span>  <!-- 时间显示异常 0-0-0-->
        </template>
      </el-table-column>
      <el-table-column label="操作" width="250" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button
            v-show = "scope.row.status == 2 && scope.row.quartz != '' "
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

    <!-- 添加或修改【转换】对话框 -->
    <el-dialog :title="title" v-model="open" width="800px" append-to-body>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="执行方式" prop="type">
          <el-select v-model="form.type" placeholder="请输入执行方式" style="width:680px"  >
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
          <el-input v-model="form.relativeLocation"  maxlength="255" show-word-limit placeholder="请输入转换文件在仓库中的完整路径,如:acplog/hw/hw_alertInteger.ktr" style="width:680px" />
        </el-form-item>

        <el-form-item label="转换名称" prop="name">
          <el-input v-model="form.name"  maxlength="255" show-word-limit placeholder="请输入转换名称,如:hw_alertInteger.ktr" style="width:680px" />
        </el-form-item>
        <el-form-item label="任务分类" prop="categoryId">
          <el-select v-model="form.categoryId" placeholder="请选择任务分类" style="width:680px" >
            <el-option
              v-for="item in CategoryList"
              :key="item.id"
              :label="item.categoryName"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="调度策略" prop="quartz">
          <el-select v-model="form.quartz" clearable placeholder="请输入转换调度策略,如转换归属于作业,作业已设置调度策略，则不需要填写" style="width:680px">
            <el-option
              v-for="item in QuartzList"
              :key="item.id"
              :label="item.quartzDescription"
              :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="日志级别" prop="logLevel">
          <el-select v-model="form.logLevel" placeholder="请输入日志级别" style="width:680px">
            <el-option
              v-for="item in LogLevelList"
              :key="item.value"
              :label="item.name"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="转换描述" prop="description">
          <el-input v-model="form.description" :autosize="{minRows:4}" type="textarea" maxlength="255" show-word-limit   placeholder="请输入转换描述" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script  setup name="Trans">
import {
  addTrans,
  changeFile,
  delTrans,
  exportTrans,
  getTrans,
  handleRun,
  listTrans,
  updateTrans,
  handleStop,checkIfUsed
} from "@/api/integration/Trans";
import {getLogLevelList, getRunTypeList} from "@/api/integration/public";
import {getStatusList} from "../../../api/integration/public";
import  Condition  from "@/api/search/condition";
import  searchParam  from "@/api/search/searchform";
import {listCategory} from "@/api/integration/Category";
import {listQuartz} from "@/api/integration/Quartz";
import {listBuildGit} from "@/api/integration/buildGit.js";

import { ref, reactive, onMounted} from 'vue';

import { useRouter, useRoute } from 'vue-router';

const router=useRouter() ;

const route=useRoute()   ;

const { proxy } = getCurrentInstance();

// 总条数
const total = ref( 0);

// 弹出层标题
const title = ref( "");

// 选中数组
const names = ref([]);

// 选中数组
const ids = ref([]);

// 【转换】表格数据
const List = ref([]);

// 任务分类列表
const CategoryList = ref([]);

// 转换类型列表
const RunTypeList = ref([]);

// 日志类型列表
const LogLevelList = ref([]);

//获取定时策略列表
const QuartzList = ref([]);

//转换状态列表
const statusList = ref([]);

// 搜索参数
const searchParams = ref([]);

const searchParamTem = ref([]);

const gitList = ref([]);

const projectList = ref([]);

// 是否显示弹出层
const open = ref(false);

// 遮罩层
const loading = ref(true);

// 非单个禁用
const single = ref(true);

// 非多个禁用
const multiple = ref(true);

// 显示搜索条件
const showSearch = ref(true);

const data = reactive({

      // 搜索参数
      searchForm: {
        name: null
      },
      // 查询参数配置对象
      queryParamsConfig: {
        name: Condition.like()
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        categoryId: null,
        name: null,
        description: null,
        type: null,
        path: null,
        transRepositoryId: null,
        quartz: null,
        syncStrategy: null,
        logLevel: null,
        params: null,
        status: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          {required: true, message: "转换名称不能为空", trigger: "blur"}
        ],
        type: [
          {required: true, message: "转换类型不能为空", trigger: "blur"}
        ],
        quartz: [
          {required: false, message: "定时策略不能为空", trigger: "blur"}
        ],
        syncStrategy: [
          {required: true, message: "同步策略不能为空", trigger: "blur"}
        ],
        logLevel: [
          {required: true, message: "日志级别不能为空", trigger: "blur"}
        ],
        params: [
          {required: true, message: "转换参数不能为空", trigger: "blur"}
        ],
        status: [
          {required: true, message: "转换状态不能为空", trigger: "blur"}
        ],
        fileId: [
          {required: true, message: "上传文件不能为空", trigger: "blur"}
        ],
        categoryId: [
          {required: true, message: "任务分类不能为空", trigger: "blur"}
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

const { searchForm, queryParamsConfig, queryParams, form, rules, Params, ParamsConfig } = toRefs(data);

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

  //获取转换状态类型
  getstatusList();

  //获取仓库清单
  getGitList();

  getList();
})



function  getUploadToken(){
      // if (this.$store.getters.token === null || this.$store.getters.token===''){
      //   // this.$message("token值为空，请重新登录!")
      // } else this.upHeaders.Authorization = "Bearer "+this.$store.getters.token;

    }

/** 查询【转换】列表 */
function  getList() {
  loading.value = true;
  searchParams.value = searchParam(queryParamsConfig.value, queryParams.value);
  listTrans(searchParams.value).then(response => {
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
    id: null ,
    categoryId: null,
    name: null,
    description: null,
    type: null,
    path: null,
    transRepositoryId: null,
    quartz: null,
    syncStrategy: null,
    logLevel: null,
    params: null,
    status: null,
    fileId: null
  };
  proxy.resetForm("formRef");
}

/**
 * 获取仓库列表
 */
function  getGitList() {
  searchParamTem.value = searchParam(ParamsConfig.value, Params.value);
  listBuildGit(searchParamTem.value).then(res => {
    gitList.value = res.rows;
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
  names.value = selection.map(item => item.name + ' ')
  single.value = selection.length !== 1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function  handleAdd() {
  reset();
  form.value.type='file';
  open.value = true;
  title.value = "添加【转换】";
}

/** 修改按钮操作 */
function  handleUpdate(row) {
  reset();
  const categoryId = row.id || ids.value ;
  getTrans(categoryId).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改【转换】";
  });
}

/** 提交按钮 */
function  submitForm() {
  proxy.$refs["formRef"].validate(valid => {
    if (valid) {
      if (form.value.id != null) {
        updateTrans(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addTrans(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
}

/**
 * 上传成功回调方法
 */
function  handleUploadFile(param) {
  var fileObj = param.file;
  // FormData 对象
  var form = new FormData();
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

/** 删除按钮操作 */
function  handleDelete(row) {
  const id = row.id || ids.value;
  let nameList = row.name || names.value;
  //避免弹出窗数据太长，只显示前15条数据
  if ( nameList.length > 15 ) {
    nameList = nameList.slice(0,15);
  }
  checkIfUsed(id).then(res => {
    if ( res.code == 200 ) {
      proxy.$confirm('是否确认删除【转换】名称为"' + nameList + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delTrans(id);
      }).then(() => {
        getList();
        proxy.$modal.msgSuccess("删除成功");
      }).catch(error => {

      })
    } else {
      proxy.$modal.msgError(res.msg)
    }
  }).catch(error => {

  })
}

/** 导出按钮操作 */
function  handleExport() {
  const queryParams = queryParams.value;
  proxy.$confirm('是否确认导出所有【转换】数据项?', "警告", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning"
  }).then(function () {
    return exportTrans(queryParams);
  }).then(response => {
    download(response.msg);
  })
}

/**
 *停止转换任务
 */
function  handleStopF(id) {
  handleStop(id).then(res => {
    res.code === 200 ? proxy.$modal.msgSuccess(res.msg) : proxy.$modal.msgError(res.msg);
    getList();
  })
}

</script>
