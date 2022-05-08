<template>
  <div class="articlePage">
    <div style="padding: 1rem">
      <el-button @click="showDialog()" type="primary" style="float: left"
        >发布新文章</el-button
      >
    </div>
    <div style="padding: 1rem">
      <h1>文章浏览</h1>
    </div>
    <el-table
      :data="data"
      border
      style="width: 100%; margin: 0 auto"
      max-height="680"
    >
      <el-table-column prop="title" label="标 题"></el-table-column>

      <el-table-column prop="userName" label="作 者" width="240">
      </el-table-column>

      <el-table-column label="操 作">
        <template slot-scope="scope" width="120">
          <el-button @click="getContent(scope.row.id)" type="primary"
            >查 看</el-button
          >
          <el-button
            @click="handleDelete(scope.row.id)"
            type="danger"
            v-if="usertype == 1 || userid == scope.row.userid"
            >删 除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <el-dialog
      title="发布文章"
      :visible.sync="dialogVisible"
      width="50%"
      :before-close="handleClose"
    >
      <div style="display: flex; justify-content: flex-end">
        <el-button type="primary" @click="createArticle()">发 布</el-button>
      </div>
      <el-form>
        <el-form-item label="标 题：">
          <el-input v-model="formData.title" />
        </el-form-item>
      </el-form>
      <div>
        <quill-editor
          ref="myLQuillEditor"
          v-model="formData.content"
          :options="editorOption"
          class="editor"
        >
        </quill-editor>
      </div>
    </el-dialog>

    <el-dialog :title="title" :visible.sync="dialogContentVisible" width="70%">
      <div class="articleDialog">
        <p style="text-align: right; padding-bottom: 1rem; padding-end: 1rem">
          作者：{{ userName }}
        </p>
        <p style="text-align: right; padding-bottom: 1rem; padding-end: 1rem">
          点击量：{{ clickTime }}
        </p>
        <p v-html="content" class="articleDialog"></p>
      </div>
    </el-dialog>
  </div>
</template>

<script>

import Cookies from 'vue-js-cookie'
import { create, getAll, findContent, deleteArticle } from '@/api/article'

const toolbarOptions = [
  ['bold', 'italic', 'underline', 'strike'], // 加粗 斜体 下划线 删除线 -----['bold', 'italic', 'underline', 'strike']
  ['blockquote', 'code-block'], // 引用  代码块-----['blockquote', 'code-block']
  [{ header: [1, 2, 3, 4, 5, 6, false] }], // 标题-----[{ header: [1, 2, 3, 4, 5, 6, false] }]
  [{ color: [] }, { background: [] }], // 字体颜色、字体背景颜色-----[{ color: [] }, { background: [] }]
  [{ font: [] }], //显示字体选择
  [{ align: [] }], // 对齐方式-----
  ['clean'], // 清除文本格式-----
  ['link', 'image'], // 链接、图片、视频-----
]

export default {
  name: 'articlePage',
  components: {
  },
  data() {
    return {
      formData: {
        title: '',
        content: '',
        userid: '',
      },
      userName: '',
      clickTime: '',
      createTime: '',
      title: '',
      content: '',
      dialogVisible: false,
      dialogContentVisible: false,
      data: null,
      usertype: JSON.parse(Cookies.get('userInfo')).type,
      userid: JSON.parse(Cookies.get('userInfo')).id,
      username: JSON.parse(Cookies.get('userInfo')).name,
      editorOption: {
        modules: {
          toolbar: toolbarOptions
        },
        theme: 'snow',
        placeholder: '请输入正文'
      }
    }
  },
  mounted() {
    this.getAllArticle()
  },
  watch: {
  },
  methods: {
    getContent(id) {
      console.log(id)
      findContent(id).then(response => {
        if (response.success) {
          console.log(response.data)
          this.content = response.data.content
          this.title = response.data.title
          this.clickTime = response.data.clickTime
          this.userName = response.data.userName
          this.dialogContentVisible = true
        }
      })
    },

    getAllArticle() {
      getAll().then(response => {
        if (response.success) {
          this.data = response.data
        }
      })
    },

    handleClose() {
      this.$confirm('离开当前页面前内容将为您保留，确认关闭？')
        .then(_ => {
          _
          this.dialogVisible = false
        })
        .catch(_ => { _ })
    },
    createArticle() {
      this.formData.userid = JSON.parse(Cookies.get('userInfo')).id
      create(this.formData).then(response => {
        if (response.success) {
          this.getAllArticle()
          this.dialogVisible = false
          this.formData.title = ''
          this.formData.content = ''
          this.$message({
            message: response.data,
            type: 'success'
          })
        }
      })
    },
    handleDelete(id) {
      deleteArticle(id).then(response => {
        if (response.success) {
          this.getAllArticle()
          this.$message({
            message: response.data,
            type: 'success'
          })
        }
      })
    },
    showDialog() {
      this.dialogVisible = true
    }
  },

}
</script>

<style lang="less" scoped>
/deep/ .el-dialog {
  height: 50rem;
}
.el-dialog el-button {
  margin-bottom: 1rem;
}
.editor {
  height: 30rem /* 500/16 */;
}
.articleDialog {
  text-align:left;
}
.addButton {
  float: left;
}

/deep/ .articleDialog img {
  max-width: 100%;
}
</style>