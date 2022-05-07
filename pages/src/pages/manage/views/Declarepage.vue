<template>
  <div class="UserPage">
    <div style="padding:1rem" v-if="usertype == 2">
      <el-button @click="showtaxDialog()" type="primary" class="addButton">增值税</el-button>
      <el-button @click="showConDialog()" type="primary" class="addButton">城建税及附加</el-button>
      <el-button @click="showEntDialog()" type="primary" class="addButton">企业所得税</el-button>
    </div>
    <div style="padding:1rem">
      <h1>纳税申报记录</h1>
    </div>
    <el-table :data="data" border style="width: 100%;margin:0 auto;" max-height="680">
      <el-table-column prop="username" label="申请人" width="140"></el-table-column>
      <el-table-column prop="taxtype" label="类型" width="200">
        <template slot-scope="scope">
          <p v-if="scope.row.taxtype == 1">增值税申请</p>
          <p v-if="scope.row.taxtype == 2">城建税及附加申请</p>
          <p v-if="scope.row.taxtype == 3">企业所得税申请</p>
        </template>
      </el-table-column>
      <el-table-column prop="status" label="状态" width="120">
        <template slot-scope="scope">
          <p v-if="scope.row.status == 2" style="color:#0000ff">待支付</p>
          <p v-if="scope.row.status == 1" style="color:#FFA500">待审批</p>
          <p v-if="scope.row.status == 3" style="color:#00ff00">已支付</p>
        </template>
      </el-table-column>
      <el-table-column label="操 作">
        <template slot-scope="scope">
          <el-button @click="showDialog(scope.row)" type="primary">查 看</el-button>
          <el-button
            @click="comfirmTax(scope.row)"
            type="warning"
            v-if="scope.row.status == 1 && usertype == 1 "
          >审 批</el-button>
          <el-button
            @click="payTax(scope.row)"
            type="success"
            v-if="scope.row.status == 2 && scope.row.userid == userid"
          >付 款</el-button>
          <el-button @click="handleDelete(scope.row)" type="danger" v-if="scope.row.status == 1">删 除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog
      title="城建税及教育费附加"
      :visible.sync="conDialogVisible"
      width="90%"
      :before-close="handleConClose"
    >
      <el-form ref="conform" :model="conform" label-width="8rem">
        <el-row :gutter="80">
          <el-col :span="6">
            <el-form-item label="所属时期起" prop="begindate">
              <el-date-picker
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd"
                v-model="conform.begindate"
                type="date"
                placeholder="选择日期"
              ></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="所属时期止" prop="enddate">
              <el-date-picker
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd"
                v-model="conform.enddate"
                type="date"
                placeholder="选择日期"
              ></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <p style="text-align:left; margin-left:4rem;margin-bottom:1rem;font-size:1rem">城市维护建设税</p>
        <el-row>
          <el-col :span="6">
            <el-form-item label="增值税" prop="constructionvat">
              <el-input v-model="conform.constructionvat"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="消费税" prop="constructionct">
              <el-input v-model="conform.constructionct"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="税率" prop="constructiontrate">
              <el-input v-model="conform.constructiontrate" disabled></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="应纳税款" prop="constructionpay">
              <el-input v-model="conform.constructionpay" disabled></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <p style="text-align:left; margin-left:4rem;margin-bottom:1rem;font-size:1rem">教育费附加</p>
        <el-row>
          <el-col :span="6">
            <el-form-item label="增值税" prop="educationvat">
              <el-input v-model="conform.educationvat"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="消费税" prop="educationct">
              <el-input v-model="conform.educationct"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="税率" prop="educationtrate">
              <el-input v-model="conform.educationtrate" disabled></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="应纳税款" prop="educationpay">
              <el-input v-model="conform.educationpay" disabled></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <p style="text-align:left; margin-left:4rem;margin-bottom:1rem;font-size:1rem">地方教育附加</p>
        <el-row>
          <el-col :span="6">
            <el-form-item label="增值税" prop="localvat">
              <el-input v-model="conform.localvat"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="消费税" prop="localct">
              <el-input v-model="conform.localct"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="税率" prop="localrate">
              <el-input v-model="conform.localrate" disabled></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="应纳税款" prop="localpay">
              <el-input v-model="conform.localpay" disabled></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="id" style="display:none">
          <el-input v-model="conform.id"></el-input>
        </el-form-item>
      </el-form>
      <el-button type="primary" @click="createCon(conform)" :disabled="comfirmBtnDisable">确 定</el-button>
      <el-button @click="handleConClose">取 消</el-button>
    </el-dialog>

    <el-dialog
      title="企业所得税"
      :visible.sync="entDialogVisible"
      width="50%"
      :before-close="handleEntClose"
    >
      <el-form label-position="left" ref="entform" :model="entform" label-width="20rem">
        <el-form-item label="1.营业收入" prop="income">
          <el-input v-model="entform.income"></el-input>
        </el-form-item>
        <el-form-item label="2.营业成本" prop="cost">
          <el-input v-model="entform.cost"></el-input>
        </el-form-item>
        <el-form-item label="3.利润总额" prop="profit" disabled>
          <el-input disabled v-model="entform.profit"></el-input>
        </el-form-item>
        <el-form-item label="4.加：特定业务计算的应纳税所得额" prop="counttaxincome">
          <el-input v-model="entform.counttaxincome"></el-input>
        </el-form-item>
        <el-form-item label="5.减：不征税收入" prop="notaxincome">
          <el-input v-model="entform.notaxincome"></el-input>
        </el-form-item>
        <el-form-item label="6.减：免税收入、减计收入、所得减免等优惠金额" prop="preferential">
          <el-input v-model="entform.preferential"></el-input>
        </el-form-item>
        <el-form-item label="7.减：固定资产加速折旧（扣除）调减额" prop="depreciation">
          <el-input v-model="entform.depreciation"></el-input>
        </el-form-item>
        <el-form-item label="8.减：弥补以前年度亏损" prop="deficit">
          <el-input v-model="entform.deficit"></el-input>
        </el-form-item>
        <el-form-item label="9.实际利润额" prop="actualprofit">
          <el-input disabled v-model="entform.actualprofit"></el-input>
        </el-form-item>
        <el-form-item label="10.税率（25%）" prop="taxrate">
          <el-input disabled v-model="entform.taxrate"></el-input>
        </el-form-item>
        <el-form-item label="11.应纳所得税额" prop="tax">
          <el-input disabled v-model="entform.tax"></el-input>
        </el-form-item>
        <el-form-item label="12.减：减免所得税额" prop="derate">
          <el-input v-model="entform.derate"></el-input>
        </el-form-item>
        <el-form-item label="13.减：实际已缴纳所得税额" prop="payedtax">
          <el-input v-model="entform.payedtax"></el-input>
        </el-form-item>
        <el-form-item label="14.减：特定业务预缴（征）所得税额" prop="beforehandtax">
          <el-input v-model="entform.beforehandtax"></el-input>
        </el-form-item>
        <el-form-item label="15.本期应补（退）所得税额" prop="actualtax">
          <el-input disabled v-model="entform.actualtax"></el-input>
        </el-form-item>

        <el-form-item label="id" style="display:none">
          <el-input v-model="entform.id"></el-input>
        </el-form-item>
      </el-form>
      <el-button type="primary" @click="createEnterprise(entform)" :disabled="comfirmBtnDisable">确 定</el-button>
      <el-button @click="handleEntClose">取 消</el-button>
    </el-dialog>

    <el-dialog
      title="增值税"
      :visible.sync="taxDialogVisible"
      width="80%"
      :before-close="handletaxClose"
    >
      <div style="text-align:right">
        <el-row :gutter="10" style="font-size:1rem;padding:0.5rem">
          <el-col :span="6">申报类型</el-col>
          <el-col :span="6">
            <el-input size="medium" value="正常申报" disabled></el-input>
          </el-col>
          <el-col :span="6">纳税人名称</el-col>
          <el-col :span="6">
            <el-input size="medium" v-model="payername" disabled></el-input>
          </el-col>
        </el-row>
        <el-row :gutter="10" style="font-size:1rem;padding:0.5rem">
          <el-col :span="6">所属时期起</el-col>
          <el-col :span="6">
            <el-date-picker
              format="yyyy-MM-dd"
              value-format="yyyy-MM-dd"
              v-model="taxform.begindate"
              type="date"
              placeholder="选择日期"
            ></el-date-picker>
          </el-col>
          <el-col :span="6">所属时期止</el-col>
          <el-col :span="6">
            <el-date-picker
              format="yyyy-MM-dd"
              value-format="yyyy-MM-dd"
              v-model="taxform.enddate"
              type="date"
              placeholder="选择日期"
            ></el-date-picker>
          </el-col>
        </el-row>
        <hr />
      </div>
      <hr />
      <hr />
      <h2 style="font-size:1rem;padding:0.5rem">销项税额表</h2>
      <div style="text-align:left">
        <el-row style="font-size:1rem;padding:0.5rem">
          <el-col :span="3">计税方法</el-col>
          <el-col :span="3">项目</el-col>
          <el-col :span="9">说明</el-col>
          <el-col :span="5">销售额</el-col>
          <el-col :span="4">销项税额</el-col>
        </el-row>
        <hr />
        <el-row style="font-size:1rem;padding:0.5rem">
          <el-col :span="3">一般计税方法</el-col>
          <el-col :span="3">全部征税项目</el-col>
          <el-col :span="9">{{taxform.taxopIns[0].type}}</el-col>
          <el-col :span="5">
            <el-input size="mini" v-model="taxform.taxopIns[0].sales"></el-input>
          </el-col>
          <el-col :span="4">
            <el-input size="mini" v-model="taxform.taxopIns[0].salestax" disabled></el-input>
          </el-col>
        </el-row>
        <hr />
        <el-row style="font-size:1rem;padding:0.5rem">
          <el-col :span="3">一般计税方法</el-col>
          <el-col :span="3">全部征税项目</el-col>
          <el-col :span="9">{{taxform.taxopIns[1].type}}</el-col>
          <el-col :span="5">
            <el-input size="mini" v-model="taxform.taxopIns[1].sales"></el-input>
          </el-col>
          <el-col :span="4">
            <el-input size="mini" v-model="taxform.taxopIns[1].salestax" disabled></el-input>
          </el-col>
        </el-row>
        <hr />
        <el-row style="font-size:1rem;padding:0.5rem">
          <el-col :span="3">一般计税方法</el-col>
          <el-col :span="3">全部征税项目</el-col>
          <el-col :span="9">{{taxform.taxopIns[2].type}}</el-col>
          <el-col :span="5">
            <el-input size="mini" v-model="taxform.taxopIns[2].sales"></el-input>
          </el-col>
          <el-col :span="4">
            <el-input size="mini" v-model="taxform.taxopIns[2].salestax" disabled></el-input>
          </el-col>
        </el-row>
        <hr />
        <el-row style="font-size:1rem;padding:0.5rem">
          <el-col :span="3">一般计税方法</el-col>
          <el-col :span="3">全部征税项目</el-col>
          <el-col :span="9">{{taxform.taxopIns[3].type}}</el-col>
          <el-col :span="5">
            <el-input size="mini" v-model="taxform.taxopIns[3].sales"></el-input>
          </el-col>
          <el-col :span="4">
            <el-input size="mini" v-model="taxform.taxopIns[3].salestax" disabled></el-input>
          </el-col>
        </el-row>
        <hr />
        <el-row style="font-size:1rem;padding:0.5rem">
          <el-col :span="3">一般计税方法</el-col>
          <el-col :span="3">全部征税项目</el-col>
          <el-col :span="9">{{taxform.taxopIns[4].type}}</el-col>
          <el-col :span="5">
            <el-input size="mini" v-model="taxform.taxopIns[4].sales"></el-input>
          </el-col>
          <el-col :span="4">
            <el-input size="mini" v-model="taxform.taxopIns[4].salestax" disabled></el-input>
          </el-col>
        </el-row>
        <hr />
        <el-row style="font-size:1rem;padding:0.5rem">
          <el-col :span="3">一般计税方法</el-col>
          <el-col :span="3">全部征税项目</el-col>
          <el-col :span="9">{{taxform.taxopIns[5].type}}</el-col>
          <el-col :span="5">
            <el-input size="mini" v-model="taxform.taxopIns[5].sales"></el-input>
          </el-col>
          <el-col :span="4">
            <el-input size="mini" v-model="taxform.taxopIns[5].salestax" disabled></el-input>
          </el-col>
        </el-row>
        <hr />
        <el-row style="font-size:1rem;padding:0.5rem">
          <el-col :span="3">一般计税方法</el-col>
          <el-col :span="3">即征即退项目</el-col>
          <el-col :span="9">{{taxform.taxopIns[6].type}}</el-col>
          <el-col :span="5">
            <el-input size="mini" v-model="taxform.taxopIns[6].sales" disabled></el-input>
          </el-col>
          <el-col :span="4">
            <el-input size="mini" v-model="taxform.taxopIns[6].salestax" disabled></el-input>
          </el-col>
        </el-row>
        <hr />
        <el-row style="font-size:1rem;padding:0.5rem">
          <el-col :span="3">一般计税方法</el-col>
          <el-col :span="3">即征即退项目</el-col>
          <el-col :span="9">{{taxform.taxopIns[7].type}}</el-col>
          <el-col :span="5">
            <el-input size="mini" v-model="taxform.taxopIns[7].sales" disabled></el-input>
          </el-col>
          <el-col :span="4">
            <el-input size="mini" v-model="taxform.taxopIns[7].salestax" disabled></el-input>
          </el-col>
        </el-row>
        <hr />
        <el-row style="font-size:1rem;padding:0.5rem">
          <el-col :span="3">简易计税方法</el-col>
          <el-col :span="3">全部征税项目</el-col>
          <el-col :span="9">{{taxform.taxopIns[8].type}}</el-col>
          <el-col :span="5">
            <el-input size="mini" v-model="taxform.taxopIns[8].sales"></el-input>
          </el-col>
          <el-col :span="4">
            <el-input size="mini" v-model="taxform.taxopIns[8].salestax" disabled></el-input>
          </el-col>
        </el-row>
        <hr />
        <el-row style="font-size:1rem;padding:0.5rem">
          <el-col :span="3">简易计税方法</el-col>
          <el-col :span="3">全部征税项目</el-col>
          <el-col :span="9">{{taxform.taxopIns[9].type}}</el-col>
          <el-col :span="5">
            <el-input size="mini" v-model="taxform.taxopIns[9].sales"></el-input>
          </el-col>
          <el-col :span="4">
            <el-input size="mini" v-model="taxform.taxopIns[9].salestax" disabled></el-input>
          </el-col>
        </el-row>
        <hr />
        <el-row style="font-size:1rem;padding:0.5rem">
          <el-col :span="3">简易计税方法</el-col>
          <el-col :span="3">即征即退项目</el-col>
          <el-col :span="9">{{taxform.taxopIns[10].type}}</el-col>
          <el-col :span="5">
            <el-input size="mini" v-model="taxform.taxopIns[10].sales" disabled></el-input>
          </el-col>
          <el-col :span="4">
            <el-input size="mini" v-model="taxform.taxopIns[10].salestax" disabled></el-input>
          </el-col>
        </el-row>
        <hr />
        <el-row style="font-size:1rem;padding:0.5rem">
          <el-col :span="3">简易计税方法</el-col>
          <el-col :span="3">即征即退项目</el-col>
          <el-col :span="9">{{taxform.taxopIns[11].type}}</el-col>
          <el-col :span="5">
            <el-input size="mini" v-model="taxform.taxopIns[11].sales" disabled></el-input>
          </el-col>
          <el-col :span="4">
            <el-input size="mini" v-model="taxform.taxopIns[11].salestax" disabled></el-input>
          </el-col>
        </el-row>
      </div>
      <hr style="margin-top:2rem" />
      <hr />
      <h2 style="font-size:1rem;padding:0.5rem">进项税额表</h2>
      <div style="text-align:left">
        <el-row style="font-size:1rem;padding:0.5rem">
          <el-col :span="9">项目</el-col>
          <el-col :span="5">份数</el-col>
          <el-col :span="5">金额</el-col>
          <el-col :span="5">税额</el-col>
        </el-row>
        <hr />
        <el-row style="font-size:1rem;padding:0.5rem">
          <el-col :span="9">{{taxform.taxipIns[0].type}}</el-col>
          <el-col :span="5">
            <el-input size="mini" v-model="taxform.taxipIns[0].amount"></el-input>
          </el-col>
          <el-col :span="5">
            <el-input size="mini" v-model="taxform.taxipIns[0].money"></el-input>
          </el-col>
          <el-col :span="5">
            <el-input size="mini" v-model="taxform.taxipIns[0].tax"></el-input>
          </el-col>
        </el-row>
        <hr />
        <el-row style="font-size:1rem;padding:0.5rem">
          <el-col :span="9">{{taxform.taxipIns[1].type}}</el-col>
          <el-col :span="5">
            <el-input size="mini" v-model="taxform.taxipIns[1].amount"></el-input>
          </el-col>
          <el-col :span="5">
            <el-input size="mini" v-model="taxform.taxipIns[1].money"></el-input>
          </el-col>
          <el-col :span="5">
            <el-input size="mini" v-model="taxform.taxipIns[1].tax"></el-input>
          </el-col>
        </el-row>
        <hr />
        <el-row style="font-size:1rem;padding:0.5rem">
          <el-col :span="9">{{taxform.taxipIns[2].type}}</el-col>
          <el-col :span="5">
            <el-input size="mini" v-model="taxform.taxipIns[2].amount"></el-input>
          </el-col>
          <el-col :span="5">
            <el-input size="mini" v-model="taxform.taxipIns[2].money"></el-input>
          </el-col>
          <el-col :span="5">
            <el-input size="mini" v-model="taxform.taxipIns[2].tax"></el-input>
          </el-col>
        </el-row>
        <hr />
        <el-row style="font-size:1rem;padding:0.5rem">
          <el-col :span="9">{{taxform.taxipIns[3].type}}</el-col>
          <el-col :span="5">
            <el-input size="mini" v-model="taxform.taxipIns[3].amount"></el-input>
          </el-col>
          <el-col :span="5">
            <el-input size="mini" v-model="taxform.taxipIns[3].money"></el-input>
          </el-col>
          <el-col :span="5">
            <el-input size="mini" v-model="taxform.taxipIns[3].tax"></el-input>
          </el-col>
        </el-row>
        <hr />
        <el-row style="font-size:1rem;padding:0.5rem">
          <el-col :span="9">{{taxform.taxipIns[4].type}}</el-col>
          <el-col :span="5">
            <el-input size="mini" v-model="taxform.taxipIns[4].amount"></el-input>
          </el-col>
          <el-col :span="5">
            <el-input size="mini" v-model="taxform.taxipIns[4].money"></el-input>
          </el-col>
          <el-col :span="5">
            <el-input size="mini" v-model="taxform.taxipIns[4].tax"></el-input>
          </el-col>
        </el-row>
        <hr />
        <el-row style="font-size:1rem;padding:0.5rem">
          <el-col :span="9">{{taxform.taxipIns[5].type}}</el-col>
          <el-col :span="5">
            <el-input size="mini" v-model="taxform.taxipIns[5].amount"></el-input>
          </el-col>
          <el-col :span="5">
            <el-input size="mini" v-model="taxform.taxipIns[5].money"></el-input>
          </el-col>
          <el-col :span="5">
            <el-input size="mini" v-model="taxform.taxipIns[5].tax"></el-input>
          </el-col>
        </el-row>
        <hr />
        <el-row style="font-size:1rem;padding:0.5rem">
          <el-col :span="9">{{taxform.taxipIns[6].type}}</el-col>
          <el-col :span="5">
            <el-input size="mini" v-model="taxform.taxipIns[6].amount"></el-input>
          </el-col>
          <el-col :span="5">
            <el-input size="mini" v-model="taxform.taxipIns[6].money" disabled></el-input>
          </el-col>
          <el-col :span="5">
            <el-input size="mini" v-model="taxform.taxipIns[6].tax"></el-input>
          </el-col>
        </el-row>
        <hr />
        <el-row style="font-size:1rem;padding:0.5rem">
          <el-col :span="9">{{taxform.taxipIns[7].type}}</el-col>
          <el-col :span="5">
            <el-input size="mini" v-model="taxform.taxipIns[7].amount" disabled></el-input>
          </el-col>
          <el-col :span="5">
            <el-input size="mini" v-model="taxform.taxipIns[7].money" disabled></el-input>
          </el-col>
          <el-col :span="5">
            <el-input size="mini" v-model="taxform.taxipIns[7].tax"></el-input>
          </el-col>
        </el-row>
        <hr />
        <el-row style="font-size:1rem;padding:0.5rem">
          <el-col :span="9">{{taxform.taxipIns[8].type}}</el-col>
          <el-col :span="5">
            <el-input size="mini" v-model="taxform.taxipIns[8].amount"></el-input>
          </el-col>
          <el-col :span="5">
            <el-input size="mini" v-model="taxform.taxipIns[8].money"></el-input>
          </el-col>
          <el-col :span="5">
            <el-input size="mini" v-model="taxform.taxipIns[8].tax"></el-input>
          </el-col>
        </el-row>
        <hr />
        <el-row style="font-size:1rem;padding:0.5rem">
          <el-col :span="9">{{taxform.taxipIns[9].type}}</el-col>
          <el-col :span="5">
            <el-input size="mini" v-model="taxform.taxipIns[9].amount"></el-input>
          </el-col>
          <el-col :span="5">
            <el-input size="mini" v-model="taxform.taxipIns[9].money"></el-input>
          </el-col>
          <el-col :span="5">
            <el-input size="mini" v-model="taxform.taxipIns[9].tax"></el-input>
          </el-col>
        </el-row>
        <hr />
        <el-row style="font-size:1rem;padding:0.5rem">
          <el-col :span="9">{{taxform.taxipIns[10].type}}</el-col>
          <el-col :span="5">
            <el-input size="mini" v-model="taxform.taxipIns[10].amount"></el-input>
          </el-col>
          <el-col :span="5">
            <el-input size="mini" v-model="taxform.taxipIns[10].money"></el-input>
          </el-col>
          <el-col :span="5">
            <el-input size="mini" v-model="taxform.taxipIns[10].tax"></el-input>
          </el-col>
        </el-row>
        <hr />
        <el-row style="font-size:1rem;padding:0.5rem">
          <el-col :span="9">{{taxform.taxipIns[11].type}}</el-col>
          <el-col :span="5">
            <el-input size="mini" v-model="taxform.taxipIns[11].amount" disabled></el-input>
          </el-col>
          <el-col :span="5">
            <el-input size="mini" v-model="taxform.taxipIns[11].money" disabled></el-input>
          </el-col>
          <el-col :span="5">
            <el-input size="mini" v-model="taxform.taxipIns[11].tax"></el-input>
          </el-col>
        </el-row>
        <hr />
        <el-row style="font-size:1rem;padding:0.5rem">
          <el-col :span="9">
            <strong>当期申报抵扣进项税额合计</strong>
          </el-col>
          <el-col :span="15">{{taxform.taxiptotal}}</el-col>
        </el-row>
      </div>
      <hr style="margin-top:2rem" />
      <hr />
      <h2 style="font-size:1rem;padding:0.5rem">总表</h2>
      <div style="text-align:left">
        <el-row style="font-size:1rem;padding:0.5rem">
          <el-col :span="14">销项税额</el-col>
          <el-col :span="10">
            <el-input size="mini" v-model="taxform.inputvat"></el-input>
          </el-col>
        </el-row>
        <hr />
        <el-row style="font-size:1rem;padding:0.5rem">
          <el-col :span="14">进项税额</el-col>
          <el-col :span="10">
            <el-input size="mini" v-model="taxform.outputvat"></el-input>
          </el-col>
        </el-row>
        <hr />
        <el-row style="font-size:1rem;padding:0.5rem">
          <el-col :span="14">上期留抵税额</el-col>
          <el-col :span="10">
            <el-input size="mini" v-model="taxform.lasttax"></el-input>
          </el-col>
        </el-row>
        <hr />
        <el-row style="font-size:1rem;padding:0.5rem">
          <el-col :span="14">进项税额转出</el-col>
          <el-col :span="10">
            <el-input size="mini" v-model="taxform.inputvatout"></el-input>
          </el-col>
        </el-row>
        <hr />
        <el-row style="font-size:1rem;padding:0.5rem">
          <el-col :span="14">免抵退应退税额</el-col>
          <el-col :span="10">
            <el-input size="mini" v-model="taxform.freetax"></el-input>
          </el-col>
        </el-row>
        <hr />
        <el-row style="font-size:1rem;padding:0.5rem">
          <el-col :span="14">按适用税率计算的纳税检查应补缴税额</el-col>
          <el-col :span="10">
            <el-input size="mini" v-model="taxform.counttax"></el-input>
          </el-col>
        </el-row>
        <hr />
        <el-row style="font-size:1rem;padding:0.5rem">
          <el-col :span="14">应抵扣税额合计</el-col>
          <el-col :span="10">
            <el-input size="mini" v-model="taxform.totalfreetax"></el-input>
          </el-col>
        </el-row>
        <hr />
        <el-row style="font-size:1rem;padding:0.5rem">
          <el-col :span="14">实际抵扣税额</el-col>
          <el-col :span="10">
            <el-input size="mini" v-model="taxform.actualtotalfreetax"></el-input>
          </el-col>
        </el-row>
        <hr />
        <el-row style="font-size:1rem;padding:0.5rem">
          <el-col :span="14">应纳税额</el-col>
          <el-col :span="10">
            <el-input size="mini" v-model="taxform.paytax"></el-input>
          </el-col>
        </el-row>
        <hr />
        <el-row style="font-size:1rem;padding:0.5rem">
          <el-col :span="14">期末留抵税额</el-col>
          <el-col :span="10">
            <el-input size="mini" v-model="taxform.enddaytax"></el-input>
          </el-col>
        </el-row>
        <hr />
        <el-row style="font-size:1rem;padding:0.5rem">
          <el-col :span="14">简易计税办法计算的应纳税额</el-col>
          <el-col :span="10">
            <el-input size="mini" v-model="taxform.easycounttax"></el-input>
          </el-col>
        </el-row>
        <hr />
        <el-row style="font-size:1rem;padding:0.5rem">
          <el-col :span="14">按简易计税办法计算的纳税检查应补缴税额</el-col>
          <el-col :span="10">
            <el-input size="mini" v-model="taxform.easycountchecktax"></el-input>
          </el-col>
        </el-row>
        <hr />
        <el-row style="font-size:1rem;padding:0.5rem">
          <el-col :span="14">应纳税额减征额</el-col>
          <el-col :span="10">
            <el-input size="mini" v-model="taxform.payfreetax"></el-input>
          </el-col>
        </el-row>
        <hr />
        <el-row style="font-size:1rem;padding:0.5rem">
          <el-col :span="14">应纳税额合计</el-col>
          <el-col :span="10">
            <el-input size="mini" v-model="taxform.totaltax" disabled></el-input>
          </el-col>
        </el-row>
      </div>
      <el-button type="primary" @click="createTax(taxform)" :disabled="comfirmBtnDisable">确 定</el-button>
      <el-button @click="handletaxClose">取 消</el-button>
    </el-dialog>

    <el-dialog
      title="缴纳税款"
      :visible.sync="payDialogVisible"
      width="30%"
      :before-close="handlepayClose"
    >
      <div style="padding-bottom:2rem">
        <img style="height:15rem;width:15rem;" :src="payImg" />
      </div>
      <el-button type="primary" @click="payBtn()">确 定</el-button>
      <el-button @click="handlepayClose">取 消</el-button>
    </el-dialog>
  </div>
</template>

<script>

import Cookies from 'vue-js-cookie'
import payImg from '~/images/payImg.jpg'
import { createContruction, createEnterprise, createTaxTotal, findMyDeclare, deleteTax, deleteCon, deleteEnt, findAll, findTaxById, findConById, findEntById, confirmDeclare } from '@/api/declare'

export default {
  name: 'UserPage',
  components: {
  },
  data() {
    return {
      payername:Cookies.get('taxpayername'),
      payImg: payImg,
      payid: '',
      payDialogVisible: false,
      comfirmBtnDisable: false,
      taxDialogVisible: false,
      entDialogVisible: false,
      conDialogVisible: false,
      data: null,
      usertype: JSON.parse(Cookies.get('userInfo')).type,
      userid: JSON.parse(Cookies.get('userInfo')).id,
      username: JSON.parse(Cookies.get('userInfo')).name,
      conform: {
        userid: JSON.parse(Cookies.get('userInfo')).id,
        constructionvat: 0,
        constructionct: 0,
        constructiontrate: 0.05,
        constructionpay: 0,
        educationvat: 0,
        educationct: 0,
        educationtrate: 0.03,
        educationpay: 0,
        localvat: 0,
        localct: 0,
        localrate: 0.03,
        localpay: 0,
        id: null
      },
      entform: {
        income: 0,
        cost: 0,
        profit: 0,
        counttaxincome: 0,
        notaxincome: 0,
        preferential: 0,
        depreciation: 0,
        deficit: 0,
        actualprofit: 0,
        taxrate: 0.25,
        tax: 0,
        derate: 0,
        payedtax: 0,
        beforehandtax: 0,
        actualtax: 0,
        userid: JSON.parse(Cookies.get('userInfo')).id,
      },
      taxform: {
        userid: JSON.parse(Cookies.get('userInfo')).id,
        inputvat: 0,
        outputvat: 0,
        lasttax: 0,
        inputvatout: 0,
        freetax: 0,
        counttax: 0,
        totalfreetax: 0,
        actualtotalfreetax: 0,
        paytax: 0,
        enddaytax: 0,
        easycounttax: 0,
        easycountchecktax: 0,
        payfreetax: 0,
        totaltax: 0,
        applytype: 0,
        begindate: null,
        enddate: null,
        taxiptotal: 0,
        taxopIns: [
          {
            sales: 0,
            salestax: 0,
            rate: 0.13,
            type: '13%税率的货物及加工休息修配劳务',
          },
          {
            sales: 0,
            salestax: 0,
            rate: 0.13,
            type: '13%税率的货物不动产和无形资产',
          },
          {
            sales: 0,
            salestax: 0,
            rate: 0.09,
            type: '9%税率的货物及加工修理修配劳务',
          },
          {
            sales: 0,
            salestax: 0,
            rate: 0.09,
            type: '9%税率的服务不动产和无形资产',
          },
          {
            sales: 0,
            salestax: 0,
            rate: 0.09,
            type: '9%税率',
          },
          {
            sales: 0,
            salestax: 0,
            rate: 0.06,
            type: '6%税率',
          },
          {
            sales: 0,
            salestax: 0,
            rate: 0,
            type: '即征即退货物及加工修理修配劳务',
          },
          {
            sales: 0,
            salestax: 0,
            rate: 0,
            type: '即征即退服务不动产和无形资产',
          },
          {
            sales: 0,
            salestax: 0,
            rate: 0.05,
            type: '5%征收率的货物及加工修理修配劳务',
          },
          {
            sales: 0,
            salestax: 0,
            rate: 0.03,
            type: '3%征收率的服务不动产和无形资产',
          },
          {
            sales: 0,
            salestax: 0,
            rate: 1,
            type: '即征即退货物及加工修理修配劳务',
          },
          {
            sales: 0,
            salestax: 0,
            rate: 1,
            type: '即征即退服务不动产和无形资产',
          },
        ],
        taxipIns: [
          {
            money: 0,
            amount: 0,
            tax: 0,
            type: '(一)认证相符的增值税专用发票',
          },
          {
            money: 0,
            amount: 0,
            tax: 0,
            type: '其中：本期认证相符且本期申报抵扣',
          },
          {
            money: 0,
            amount: 0,
            tax: 0,
            type: '前期认证相符且本期申报抵扣',
          },
          {
            money: 0,
            amount: 0,
            tax: 0,
            type: '（二）其他扣税凭证',
          },
          {
            money: 0,
            amount: 0,
            tax: 0,
            type: '其中：海关进口增值税专用缴款书',
          },
          {
            money: 0,
            amount: 0,
            tax: 0,
            type: '农产品收购发票或者销售发票',
          },
          {
            money: 0,
            amount: 0,
            tax: 0,
            type: '代扣代缴税收缴款凭证',
          },
          {
            money: 0,
            amount: 0,
            tax: 0,
            type: '加计扣除农产品进项税额',
          },
          {
            money: 0,
            amount: 0,
            tax: 0,
            type: '其他',
          },
          {
            money: 0,
            amount: 0,
            tax: 0,
            type: '（三）本期用于购建不动产的扣税凭证',
          },
          {
            money: 0,
            amount: 0,
            tax: 0,
            type: '（四）本期用于抵扣的旅客运输服务扣税凭证',
          },
          {
            money: 0,
            amount: 0,
            tax: 0,
            type: '（五）外贸企业进项税额抵扣证明',
          },
        ]
      }
    }
  },
  mounted() {
    this.findMyDeclare()
  },
  watch: {
    conform: {
      handler() {
        this.conform.constructionpay = this.conform.constructionvat * this.conform.constructiontrate + this.conform.constructionct * this.conform.constructiontrate
        this.conform.educationpay = this.conform.educationvat * this.conform.educationtrate + this.conform.educationct * this.conform.educationtrate
        this.conform.localpay = this.conform.localvat * this.conform.localrate + this.conform.localct * this.conform.localrate
      },
      deep: true,
    },
    entform: {
      handler() {
        let e = this.entform
        e.profit = e.income - e.cost
        let temp = e.profit * 1 + e.counttaxincome * 1
        e.actualprofit = temp - e.notaxincome - e.preferential - e.depreciation - e.deficit
        e.tax = e.actualprofit * e.taxrate
        e.actualtax = e.tax - e.derate - e.payedtax - e.beforehandtax
      },
      deep: true,
    },
    taxform: {
      handler() {
        let _op = this.taxform.taxopIns
        _op[0].salestax = _op[0].sales * _op[0].rate
        _op[1].salestax = _op[1].sales * _op[1].rate
        _op[2].salestax = _op[2].sales * _op[2].rate
        _op[3].salestax = _op[3].sales * _op[3].rate
        _op[4].salestax = _op[4].sales * _op[4].rate
        _op[5].salestax = _op[5].sales * _op[5].rate
        _op[6].salestax = _op[6].sales * _op[6].rate
        _op[7].salestax = _op[7].sales * _op[7].rate
        _op[8].salestax = _op[8].sales * _op[8].rate
        _op[9].salestax = _op[9].sales * _op[9].rate
        _op[10].salestax = _op[10].sales * _op[10].rate
        _op[11].salestax = _op[11].sales * _op[11].rate

        let _ip = this.taxform.taxipIns
        let _ = this.taxform
        _.taxiptotal = _ip[0].tax * 1 + _ip[1].tax * 1 + _ip[2].tax * 1 + _ip[3].tax * 1 + _ip[4].tax * 1 + _ip[5].tax * 1 + _ip[6].tax * 1 + _ip[7].tax * 1 + _ip[8].tax * 1 + _ip[9].tax * 1 + _ip[10].tax * 1 + _ip[11].tax * 1
        _.totaltax = _.inputvat - _.outputvat
      },
      deep: true,
    },
  },
  methods: {
    payBtn() {
      confirmDeclare(this.payid).then(response => {
        this.payDialogVisible = false
        this.findMyDeclare()
        if (response.success) {
          this.$message({
            message: response.data,
            type: 'success'
          })
        }
      })
    },
    handlepayClose() {
      this.payDialogVisible = false
    },
    payTax(row) {
      this.payid = row.id
      this.payDialogVisible = true
    },
    comfirmTax(row) {
      confirmDeclare(row.id).then(response => {
        this.findMyDeclare()
        if (response.success) {
          this.$message({
            message: response.data,
            type: 'success'
          })
        }
      })
    },
    handleDelete(row) {
      if (row.taxtype == 1) {
        deleteTax(row.id).then(response => {
          this.findMyDeclare()
          if (response.success) {
            this.$message({
              message: response.data,
              type: 'success'
            })
          }
        })
      }
      if (row.taxtype == 2) {
        deleteCon(row.id).then(response => {
          this.findMyDeclare()
          if (response.success) {
            this.$message({
              message: response.data,
              type: 'success'
            })
          }
        })
      }
      if (row.taxtype == 3) {
        deleteEnt(row.id).then(response => {
          this.findMyDeclare()
          if (response.success) {
            this.$message({
              message: response.data,
              type: 'success'
            })
          }
        })
      }
      this.findMyDeclare()
    },
    showDialog(row) {
      this.comfirmBtnDisable = true
      if (row.taxtype == 1) {
        findTaxById(row.id).then(response => {
          this.taxform = response.data
          this.taxform.userid = this.userid
          this.taxDialogVisible = true
        })
      }
      if (row.taxtype == 2) {
        findConById(row.id).then(response => {
          this.conform = response.data
          this.conform.userid = this.userid
          this.conDialogVisible = true
        })
      }
      if (row.taxtype == 3) {
        findEntById(row.id).then(response => {
          this.entform = response.data
          this.entform.userid = this.userid
          this.entDialogVisible = true
        })
      }
    },
    findMyDeclare() {
      if (this.usertype == 1) {
        findAll().then(response => {
          this.data = response.data
        })
      } else {
        findMyDeclare(this.userid).then(response => {
          this.data = response.data
        })
      }
    },
    createTax(form) {
      createTaxTotal(form).then(response => {
        this.findMyDeclare()
        this.taxDialogVisible = false
        if (response.success) {
          this.$message({
            message: response.data,
            type: 'success'
          })
        }
      })
    },
    createEnterprise(form) {
      createEnterprise(form).then(response => {
        this.findMyDeclare()
        this.entDialogVisible = false
        this.entform.income = 0
        this.entform.cost = 0
        this.entform.profit = 0
        this.entform.counttaxincome = 0
        this.entform.notaxincome = 0
        this.entform.preferential = 0
        this.entform.depreciation = 0
        this.entform.deficit = 0
        this.entform.actualprofit = 0
        this.entform.taxrate = 0.25
        this.entform.tax = 0
        this.entform.derate = 0
        this.entform.payedtax = 0
        this.entform.beforehandtax = 0
        this.entform.actualtax = 0
        this.entform.id = ''
        if (response.success) {
          this.$message({
            message: response.data,
            type: 'success'
          })
        }
      })
    },
    createCon(form) {
      createContruction(form).then(response => {
        this.findMyDeclare()
        this.conDialogVisible = false
        this.conform.begindate = null
        this.conform.enddate = null
        this.conform.constructionvat = 0
        this.conform.constructionct = 0
        this.conform.constructiontrate = 0.05
        this.conform.constructionpay = 0
        this.conform.educationvat = 0
        this.conform.educationct = 0
        this.conform.educationtrate = 0.03
        this.conform.educationpay = 0
        this.conform.localvat = 0
        this.conform.localct = 0
        this.conform.localrate = 0.03
        this.conform.localpay = 0
        this.conform.id = ''
        if (response.success) {
          this.$message({
            message: response.data,
            type: 'success'
          })
        }
      })
    },
    showConDialog() {
      this.conDialogVisible = true
    },

    handleConClose() {
      this.$confirm('确认关闭？')
        .then(_ => {
          this.comfirmBtnDisable = false
          this.conDialogVisible = false
          this.conform.constructionvat = 0
          this.conform.constructionct = 0
          this.conform.constructiontrate = 0.05
          this.conform.constructionpay = 0
          this.conform.educationvat = 0
          this.conform.educationct = 0
          this.conform.educationtrate = 0.03
          this.conform.educationpay = 0
          this.conform.localvat = 0
          this.conform.localct = 0
          this.conform.localrate = 0.03
          this.conform.localpay = 0
          this.conform.id = ''
          _
        })
        .catch(_ => { _ })
    },

    showtaxDialog() {
      this.taxDialogVisible = true

    },
    showEntDialog() {
      this.entDialogVisible = true
    },

    handletaxClose() {
      this.$confirm('确认关闭？')
        .then(_ => {
          this.comfirmBtnDisable = false
          this.taxDialogVisible = false
          _
        })
        .catch(_ => { _ })
    },

    handleEntClose() {
      this.$confirm('确认关闭？')
        .then(_ => {
          this.comfirmBtnDisable = false
          this.entDialogVisible = false
          this.entform.income = 0
          this.entform.cost = 0
          this.entform.profit = 0
          this.entform.counttaxincome = 0
          this.entform.notaxincome = 0
          this.entform.preferential = 0
          this.entform.depreciation = 0
          this.entform.deficit = 0
          this.entform.actualprofit = 0
          this.entform.taxrate = 0.25
          this.entform.tax = 0
          this.entform.derate = 0
          this.entform.payedtax = 0
          this.entform.beforehandtax = 0
          this.entform.actualtax = 0
          this.entform.id = ''
          _
        })
        .catch(_ => { _ })
    },

  }
}
</script>

<style lang="less">
.UserPage {
  text-align: center;
}
.addButton {
  float: left;
}
</style>