<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/template" id="request-args-template" class="hide">
    <div v-bind:class="{'div-editing-table':editing,'request-args':true}" :data-pid="pid" :data-module-name="name">
        <div class="div-table-line"
             v-bind:class="{'div-editing-line':editing}" v-for="(item,index) in requestArgs" :key="item.id" :data-id="item.id">
            <div v-if="editing">
                <ul class="cb">
                    <li class="col-sm-1">
                        <i class="iconfont icon-close" v-on:click="removeRow(item,requestArgs)"></i>
                        <i class="iconfont icon-tianjia" v-show="item.type && ( item.type.indexOf('object')!=-1) "
                           v-on:click="insertRow(item)"></i>
                        <i class="iconfont icon-drag-copy" v-on:dragstart="dragstart(requestArgs,item)"></i>
                    </li>
                    <li class="col-sm-3 input">
                        <input type="text" list="requestlist" class="text name" v-model="item.name" :value="item.name" placeholder="请输入请求参数">
                    </li>
                    <li class="col-sm-1"><select v-model="item.require">
                        <option value="true">是</option>
                        <option value="false">否</option>
                    </select></li>
                    <li class="col-sm-2">
                        <select v-model="item.type">
                            <option value="string">string</option>
                            <option value="number">number</option>
                            <option value="boolean">boolean</option>
                            <option value="object">object</option>
                            <option value="array">array</option>
                            <option value="array[number]">array[number]</option>
                            <option value="array[boolean]">array[boolean]</option>
                            <option value="array[string]">array[string]</option>
                            <option value="array[object]">array[object]</option>
                            <option value="array[array]">array[array]</option>
                            <option value="file">file</option>
                        </select>
                    </li>
                    <li class="col-sm-1 input">
                        <input type="text" class="text" v-model="item.defaultValue" placeholder="默认值"
                                                      :value="item.defaultValue"></li>
                    <li class="col-sm-4 input full-height">
                        <textarea class="text rich-text" v-model="item.description">{{item.description}}</textarea>
                    </li>
                </ul>
            </div>
            <div v-else>
                <ul class="cb">
                    <li class="col-sm-2 name">
                        <div v-if="item.type &&(item.type.indexOf('object')!=-1)">
                            <i class="iconfont icon-my open" v-on:click="apiArgsColumnFold($event)"></i>
                        </div>
                        <div class="w-block">
                            <div class="w-item">{{item.name}}</div>
                        </div>
                    </li>
                    <li class="col-sm-1"> {{item.require || 'false'}}</li>
                    <li class="col-sm-1" :title="item.type"> {{item.type}}</li>
                    <li class="col-sm-2" :title="item.defaultValue"> {{item.defaultValue}}</li>
                    <li class="col-sm-6 full-height" :title="item.description">
                        <div class="w-block">
                            <div class="w-item">{{item.description}}</div>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="sub">
                <request-args-vue v-bind:request-args="item.children" :name="name" v-bind:editing="editing"></request-args-vue>
            </div>
        </div>
    </div>
</script>
<script>
    requirejs(['vue','${assets}/js/project/doc/component/table.js'],function(Vue,table){
        table = $.extend(true,{},table);
        table.template=document.getElementById('request-args-template').innerHTML;
        table.props=['requestArgs','editing','name', 'pid'];
        Vue.component('RequestArgsVue',table);
    });
</script>

