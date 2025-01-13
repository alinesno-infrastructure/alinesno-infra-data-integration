<template>
  <!--
    【任务分类】 功能列表
    @author paul
    @version 1.0.0
  -->
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px" @submit.native.prevent>
      <el-form-item label="分类名称" prop="categoryName">
        <el-input
          v-model="queryParams.categoryName"
          placeholder="请输入分类名称"
          clearable
          size="small"
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

        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="Edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"

        >修改</el-button>
      </el-col>
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
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="warning"-->
<!--          plain-->
<!--          icon="el-icon-download"-->
<!--          size="mini"-->
<!--          @click="export2Excel"-->
<!--        >导出</el-button>-->
<!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="CategoryList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="分类名称" align="left" prop="categoryName" />
      <el-table-column label="操作" width="200" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button
            size="mini"
            type="text"
            icon="Edit"
            @click="handleUpdate(scope.row)"

          >修改</el-button>
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

    <!-- 添加或修改【任务分类】对话框 -->
    <el-dialog :title="title" v-model="open" width="800px" append-to-body>
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="分类名称" prop="categoryName">
          <el-input v-model="form.categoryName" placeholder="请输入分类名称" maxlength="255" show-word-limit />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script  setup name="Category">

import { listCategory, getCategory, delCategory, addCategory, updateCategory, exportCategory, checkIfUsed } from "@/api/integration/Category";
import  Condition  from "@/api/search/condition";
import  searchParam  from "@/api/search/searchform";
import {listJobMonitor} from "@/api/integration/JobMonitor";

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

// 遮罩层
const loading = ref(true);

// 非单个禁用
const single = ref(true);

// 非多个禁用
const multiple = ref(true);

// 显示搜索条件
const showSearch = ref(true);

// 选中数组
const names = ref([]);

// 选中数组
const ids = ref([]);

// 【任务分类】表格数据
const CategoryList = ref([]);

const exportCategoryList = ref([]);

const searchParams = ref([]);

const data = reactive({
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        categoryName: null
      },
      // 查询参数配置对象
      queryParamsConfig: {
        categoryName: Condition.like()
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        categoryName: [
          { required: true, message: "categoryName不能为空", trigger: "blur" }
        ]
      },
      // 查询参数
      exportqueryParams: {
        pageNum: 1,
        pageSize: 10,
        categoryName: null
      }
});

const { queryParams, queryParamsConfig, form, rules, exportqueryParams } = toRefs(data);

// 页面加载后触发
onMounted(() => {
  getList();
})


/** 查询【任务分类】列表 */
function  getList() {
  debugger
  loading.value = true;
  searchParams.value = searchParam(queryParamsConfig.value, queryParams.value);
  listCategory(searchParams.value).then(response => {
    CategoryList.value = response.rows;
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
    categoryName: null
  };
  proxy.resetForm("formRef");
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

// 多选框选中数据
function  handleSelectionChange(selection) {
  ids.value = selection.map(item => item.id)
  names.value = selection.map(item => item.categoryName + ' ')
  single.value = selection.length!==1
  multiple.value = !selection.length
}

/** 新增按钮操作 */
function  handleAdd() {
  reset();
  open.value = true;
  title.value = "添加【任务分类】";
}

/** 修改按钮操作 */
function  handleUpdate(row) {
  reset();
  const id = row.id || ids.value
  getCategory(id).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改【任务分类】";
  });
}

/** 提交按钮 */
function  submitForm() {
  proxy.$refs["formRef"].validate(valid => {
    if ( valid ) {
      if ( form.value.id != null ) {
        updateCategory(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addCategory(form.value).then(response => {
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
  let categoryNameList = row.categoryName || names.value;
  //避免弹出窗数据太长，只显示前15条数据
  if ( categoryNameList.length > 15 ) {
    categoryNameList = categoryNameList.slice(0,15);
  }

  checkIfUsed(id).then(res => {
    if ( res.code == 200 ) {
      proxy.$confirm('是否确认删除【任务分类】名称为"' + categoryNameList + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return delCategory(id);
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
  proxy.$confirm('是否确认导出所有【任务分类】数据项?', "警告", {
      confirmButtonText: "确定",
      cancelButtonText: "取消",
      type: "warning"
    }).then(function() {
      return exportCategory(queryParams);
    }).then(response => {
      download(response.msg);
    })
}

function  export2Excel() {
  loading.value = true;
  exportqueryParams.value.pageSize =total.value;
  searchParams.value = searchParam(queryParamsConfig.value, exportqueryParams.value);
  listCategory(searchParams.value).then(response => {
    exportCategoryList.value = response.rows;
    require.ensure([], () => {
      const { export_json_to_excel } = require('@/excel/Export2Excel');
      const tHeader = ['分类名称'];
      // 上面设置Excel的表格第一行的标题
      const filterVal = ['categoryName'];
      // 上面的categoryName是tableData里对象的属性
      const list = exportCategoryList.value;  //把data里的tableData存到list
      const data = formatJson(filterVal, list);
      export_json_to_excel(tHeader, data, '任务分类');
    })
    loading.value = false;
  }).catch(
    loading.value = false
  );

}

function  formatJson(filterVal, jsonData) {
  return jsonData.map(v => filterVal.map(j => v[j]))
}

</script>
