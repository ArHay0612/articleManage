<template>
  <div class="articlePage">
    <div style="padding: 1rem">
      <el-button @click="showDialog()" type="primary" style="float: left"
        >发布新文章</el-button
      >
    </div>
    <div style="padding: 0.5rem">
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
      class="editDialog"
      top="5rem"
      title="发布文章"
      :visible.sync="dialogVisible"
      width="50%"
      :before-close="handleClose"
      :close-on-click-modal="false"
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
          ref="myQuillEditor"
          v-model="formData.content"
          :options="editorOption"
          class="editor"
        >
        </quill-editor>

        <el-upload
          :on-success="quillSuccess"
          :headers="headers"
          :auto-upload="false"
          style="display: none"
          drag
          multiple
          accept="image/jpeg,image/gif,image/png"
          class="quill-upload"
          :on-change="onUploadChange"
          :file-list="fileList"
          action="#"
        >
          <el-button size="small" type="primary" class="uplaod-image-btn"
            >点击上传</el-button
          >
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">
            将文件拖到此处，或
            <em>点击上传</em>
          </div>
          <div slot="tip" class="el-upload__tip">
            只能上传jpg/png文件，且不超过500kb
          </div>
        </el-upload>
      </div>
    </el-dialog>

    <el-dialog
      top="3rem"
      :visible.sync="dialogContentVisible"
      width="70%"
      :title="title"
      :close-on-click-modal="false"
    >
      <template slot="title">
        <h1
          style="
            width: 100%;
            word-break: break-all;
            word-wrap: break-word;
            line-height: 2rem;
          "
        >
          {{ title }}
        </h1>
      </template>
      <p style="text-align: right; padding-bottom: 1rem; padding-end: 1rem">
        作者：{{ userName }}
      </p>
      <p style="text-align: right; padding-bottom: 1rem; padding-end: 1rem">
        点击量：{{ clickTime }}
      </p>
      <div class="articleDialog">
        <p
          style="
            max-height: 30rem;
            overflow: auto;
            word-break: break-all;
            word-wrap: break-word;
          "
          v-html="content"
        ></p>
      </div>
      <div>
        <HR style="margin-top: 2rem" />
        <p
          style="
            padding: 1rem;
            text-align: left;
            font-weight: bold;
            font-size: 1rem;
          "
        >
          评论
        </p>
        <el-input
          type="textarea"
          :rows="2"
          placeholder="请文明发言"
          v-model="comment"
        />
        <div style="text-align: right">
          <el-button @click="createComment()" type="success" size="small"
            >发 布</el-button
          >
        </div>

        <HR style="margin-top: 1rem; margin-bottom: 1rem" />
        <div v-show="commentList == ''">暂无评论赶紧留言吧~</div>
        <div
          v-show="commentList != ''"
          class="comment-list"
          v-for="comment in commentList"
          :key="comment.id"
        >
          <div>
            <p style="font-size: 1.2rem; font-weight: bold; text-align: left">
              {{ comment.userName }}
            </p>
          </div>
          <div style="margin: 0.5rem 2rem; font-size: 1rem; text-align: left">
            {{ comment.comment }}
          </div>
          <div style="text-align: left; font-size: 0.7rem; color: #999">
            {{ comment.createTime }}
            <a
              v-show="comment.userid == userid || usertype == 1"
              style="color: blue; text-decoration: underline"
              @click="deleteComment(comment.id)"
            >
              删 除
            </a>
          </div>

          <HR style="margin-top: 1rem; margin-bottom: 1rem" />
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>

import Cookies from 'vue-js-cookie'
import { create, getAll, findContent, deleteArticle, uploadFile } from '@/api/article'
import { createCommet, findAllByArticle, deleteComment } from '@/api/comment'

const toolbarOptions = [
  ['bold', 'italic', 'underline', 'strike'], // 加粗 斜体 下划线 删除线 -----['bold', 'italic', 'underline', 'strike']
  [{ header: [1, 2, 3, 4, 5, 6, false] }], // 标题-----[{ header: [1, 2, 3, 4, 5, 6, false] }]
  [{ color: [] }, { background: [] }], // 字体颜色、字体背景颜色-----[{ color: [] }, { background: [] }]
  [{ font: [] }], //显示字体选择
  ['image'], // 链接、图片、视频-----
  ['clean'], // 清除文本格式-----
]

export default {
  name: 'articlePage',
  components: {
  },
  data() {
    return {
      editorOption: {
        theme: 'snow',
        placeholder: '请输入正文',
        modules: {
          toolbar: {
            container: toolbarOptions,
            handlers: {
              // 重写点击组件上的图片按钮要执行的代码
              'image': function (value) {
                if (value) {
                  document.querySelector('.uplaod-image-btn').click()
                }
              }
            }
          }
        }
      },
      files: null,
      formData: {
        title: '',
        content: '',
        userid: '',
      },
      form: {
        file: ''
      },
      fileList: [],
      headers: {
        'Content-Type': 'multipart/form-data'
      },
      commentList: '',
      articleId: '',
      comment: '',
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
      username: JSON.parse(Cookies.get('userInfo')).name
    }
  },
  mounted() {
    this.getAllArticle()
  },
  watch: {
  },
  methods: {
    quillSuccess(response) {
      if (response.success) {

        // 获取文本编辑器
        const quill = this.$refs.myQuillEditor.quill
        // 获取光标位置
        const pos = quill.getSelection().index
        // 插入图片到光标位置
        console.log(response.data)
        quill.insertEmbed(pos, 'image', response.data)
      } else {
        this.$essage.error('图片插入失败')
      }
    },

    onUploadChange(file, fileList) {
      let data = new FormData()
      data.append('file', file.raw)
      console.log(fileList)
      console.log(file)
      this.form.file = file.raw
      uploadFile(data).then(response => {
        console.log(response)
        this.quillSuccess(response)
      })
    },
    deleteComment(id) {
      this.$confirm('确认删除评论？')
        .then(_ => {
          deleteComment(id).then(response => {
            if (response.success) {
              findAllByArticle(this.articleId).then(response => {
                this.commentList = response.data
              })
              this.$message({
                message: response.data,
                type: 'success'
              })
            }
          })
          _
          this.dialogVisible = false
        })

    },
    createComment() {
      createCommet(this.comment, this.articleId, this.userid).then(response => {
        if (response.success) {
          this.comment = ''
          findAllByArticle(this.articleId).then(response => {
            this.commentList = response.data
          })
          this.$message({
            message: response.data,
            type: 'success'
          })
        }
      })
    },
    getContent(id) {
      let _id = id
      findContent(_id).then(response => {
        if (response.success) {
          findAllByArticle(_id).then(response => {
            this.commentList = response.data
            console.log(this.commentList)
          })
          this.articleId = response.data.id
          console.log(response.data)
          this.comment = ''
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
      this.$confirm('确认删除？')
        .then(_ => {
          _
          deleteArticle(id).then(response => {
            if (response.success) {
              this.getAllArticle()
              this.$message({
                message: response.data,
                type: 'success'
              })
            }
          })
        })
        .catch(_ => { _ })

    },
    showDialog() {
      this.dialogVisible = true
    }
  },

}
</script>

<style lang="less" scoped>
.comment-list {
  margin: 0 3rem;
}

/deep/ .editDialog .el-dialog {
  height: 50rem;
}
.el-dialog el-button {
  margin-bottom: 1rem;
}

.editor {
  height: 30rem /* 500/16 */;
}

.articleDialog {
  text-align: left;
}

.addButton {
  float: left;
}

/deep/ .articleDialog img {
  max-width: 100%;
}
</style>