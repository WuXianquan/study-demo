/ *！
 * jQuery表单插件
 *版本：3.51.0-2014.06.20
 *需要jQuery v1.5或更高版本
 *版权所有（c）2014 M. Alsup
 *示例和文档：http：//malsup.com/jquery/form/
 *项目存储库：https：//github.com/malsup/form
 *根据MIT和GPL许可证进行双重许可。
 * https://github.com/malsup/form#copyright-and-license
 * /
/ *全局ActiveXObject * /

// AMD支持
（功能（工厂）{
    “严格使用”;
    if（typeof define ==='function'&& define.amd）{
        //使用AMD; 注册为anon模块
        define（['jquery']，factory）;
    } else {
        //没有AMD; 直接调用
        factory（（typeof（jQuery）！='undefined'）？jQuery：window.Zepto）;
    }
}

（function（$）{
“严格使用”;

/ *
    使用说明：
    -----------
    不要在同一表单上同时使用ajaxSubmit和ajaxForm。这些
    功能是互斥的。如果需要，请使用ajaxSubmit
    将您自己的提交处理程序绑定到表单。例如，

    $（document）.ready（function（）{
        $（'＃myForm'）。on（'submit'，function（e）{
            e.preventDefault（）; // < - 很重要
            $（本）.ajaxSubmit（{
                目标：'＃output'
            }）;
        }）;
    }）;

    如果希望插件管理所有事件绑定，请使用ajaxForm
    为了你。例如，

    $（document）.ready（function（）{
        $（ '＃myForm的'）。给ajaxForm（{
            目标：'＃output'
        }）;
    }）;

    你也可以使用带有委托的ajaxForm（需要jQuery v1.7 +），所以
    调用ajaxForm时不必存在表单：

    $（ '＃myForm的'）。给ajaxForm（{
        代表团：是的，
        目标：'＃output'
    }）;

    使用ajaxForm时，将为您调用ajaxSubmit函数
    在适当的时候。
* /

/ **
 *特征检测
 * /
var feature = {};
feature.fileapi = $（“<input type ='file'/>”）。get（0）.files！== undefined;
feature.formdata = window.FormData！== undefined;

var hasProp = !! $。fn.prop;

// attr2可以使用prop但可以检查返回类型
//期望的字符串 这说明了表格的情况
//包含名称为“action”或“method”的输入; 在那些
//个案“prop”返回元素
$ .fn.attr2 = function（）{
    if（！hasProp）{
        返回this.attr.apply（this，arguments）;
    }
    var val = this.prop.apply（this，arguments）;
    if（（val && val.jquery）|| typeof val ==='string'）{
        返回;
    }
    返回this.attr.apply（this，arguments）;
};

/ **
 * ajaxSubmit（）提供了一种立即提交的机制
 *使用AJAX的HTML表单。
 * /
$ .fn.ajaxSubmit = function（options）{
    / * jshint scripturl：true * /

    //如果没有选择，快速失败（http://dev.jquery.com/ticket/2752）
    if（！this.length）{
        log（'ajaxSubmit：跳过提交过程 - 没有选择元素'）;
        归还这个;
    }

    var method，action，url，$ form = this;

    if（typeof options =='function'）{
        options = {success：options};
    }
    else if（options === undefined）{
        options = {};
    }

    method = options.type || this.attr2（ '方法'）;
    action = options.url || this.attr2（ '动作'）;

    url =（typeof action ==='string'）？$ .trim（action）：'';
    url = url || window.location.href || '';
    if（url）{
        //清理网址（不包括哈希值）
        url =（url.match（/ ^（[^＃] +）/）|| []）[1];
    }

    options = $ .extend（true，{
        url：url，
        成功：$ .ajaxSettings.success，
        type：方法|| $ .ajaxSettings.type，
        iframeSrc：/^https/i.test(window.location.href ||''）？'javascript：false'：'about：blank'
    }，options）;

    //用于在提取表单数据之前操作表单数据的钩子;
    //方便与tinyMCE或FCKEditor等丰富的编辑器一起使用
    var veto = {};
    this.trigger（'form-pre-serialize'，[this，options，veto]）;
    if（veto.veto）{
        log（'ajaxSubmit：通过form-pre-serialize触发器提交否决'）;
        归还这个;
    }

    //提供在序列化之前更改表单数据的机会
    if（options.beforeSerialize && options.beforeSerialize（this，options）=== false）{
        log（'ajaxSubmit：提交通过beforeSerialize回调中止'）;
        归还这个;
    }

    var traditional = options.traditional;
    if（传统=== undefined）{
        traditional = $ .ajaxSettings.traditional;
    }

    var elements = [];
    var qx，a = this.formToArray（options.semantic，elements）;
    if（options.data）{
        options.extraData = options.data;
        qx = $ .param（options.data，traditional）;
    }

    //为预提交回调提供中止提交的机会
    if（options.beforeSubmit && options.beforeSubmit（a，this，options）=== false）{
        log（'ajaxSubmit：提交通过beforeSubmit回调中止'）;
        归还这个;
    }

    //消防否决'验证'事件
    this.trigger（'form-submit-validate'，[a，this，options，veto]）;
    if（veto.veto）{
        log（'ajaxSubmit：通过form-submit-validate触发器提交否决'）;
        归还这个;
    }

    var q = $ .param（a，traditional）;
    if（qx）{
        q =（q？（q +'＆'+ qx）：qx）;
    }
    if（options.type.toUpperCase（）=='GET'）{
        options.url + =（options.url.indexOf（'？'）> = 0？'＆'：'？'）+ q;
        options.data = null; //'get'的数据为null
    }
    其他{
        options.data = q; // data是'post'的查询字符串
    }

    var callbacks = [];
    if（options.resetForm）{
        callbacks.push（function（）{$ form.resetForm（）;}）;
    }
    if（options.clearForm）{
        callbacks.push（function（）{$ form.clearForm（options.includeHidden）;}）;
    }

    //仅在未提供dataType时才对目标执行加载
    if（！options.dataType && options.target）{
        var oldSuccess = options.success || 功能（）{};
        callbacks.push（function（data）{
            var fn = options.replaceTarget？'replaceWith'：'html';
            $（options.target）[fn]（data）.each（oldSuccess，arguments）;
        }）;
    }
    else if（options.success）{
        callbacks.push（options.success）;
    }

    options.success = function（data，status，xhr）{// jQuery 1.4+将xhr作为第3个arg传递
        var context = options.context || 这个 ; // jQuery 1.4+支持范围上下文
        for（var i = 0，max = callbacks.length; i <max; i ++）{
            callbacks [i] .apply（context，[data，status，xhr || $ form，$ form]）;
        }
    };

    if（options.error）{
        var oldError = options.error;
        options.error = function（xhr，status，error）{
            var context = options.context || 这个;
            oldError.apply（context，[xhr，status，error，$ form]）;
        };
    }

     if（options.complete）{
        var oldComplete = options.complete;
        options.complete = function（xhr，status）{
            var context = options.context || 这个;
            oldComplete.apply（context，[xhr，status，$ form]）;
        };
    }

    //有文件要上传吗？

    // [value]（问题＃113），另见评论：
    // https://github.com/malsup/form/commit/588306aedba1de01388032d5f42a60159eea9228#commitcomment-2180219
    var fileInputs = $（'input [type = file]：enabled'，this）.filter（function（）{return $（this）.val（）！=='';}）;

    var hasFileInputs = fileInputs.length> 0;
    var mp ='multipart / form-data';
    var multipart =（$ form.attr（'enctype'）== mp || $ form.attr（'encoding'）== mp）;

    var fileAPI = feature.fileapi && feature.formdata;
    log（“fileAPI：”+ fileAPI）;
    var shouldUseFrame =（hasFileInputs || multipart）&&！fileAPI;

    var jqxhr;

    // options.iframe允许用户强制iframe模式
    // 06-NOV-09：如果检测到文件输入，现在默认为iframe模式
    if（options.iframe！== false &&（options.iframe || shouldUseFrame））{
        //修复Safari挂起（感谢Tim Molendijk）
        //请参阅：http：//groups.google.com/group/jquery-dev/browse_thread/thread/36395b7ab510dd5d
        if（options.closeKeepAlive）{
            $ .get（options.closeKeepAlive，function（）{
                jqxhr = fileUploadIframe（a）;
            }）;
        }
        其他{
            jqxhr = fileUploadIframe（a）;
        }
    }
    else if（（hasFileInputs || multipart）&& fileAPI）{
        jqxhr = fileUploadXhr（a）;
    }
    其他{
        jqxhr = $ .ajax（options）;
    }

    $ form.removeData（'jqxhr'）。data（'jqxhr'，jqxhr）;

    //清除元素数组
    for（var k = 0; k <elements.length; k ++）{
        elements [k] = null;
    }

    // fire'reform'事件
    this.trigger（'form-submit-notify'，[this，options]）;
    归还这个;

    //实用程序fn用于深度序列化
    function deepSerialize（extraData）{
        var serialized = $ .param（extraData，options.traditional）.split（'＆'）;
        var len = serialized.length;
        var result = [];
        var i，part;
        for（i = 0; i <len; i ++）{
            //＃252; 撤消param空间替换
            serialized [i] = serialized [i] .replace（/ \ + / g，''）;
            part = serialized [i] .split（'='）;
            //＃278; 使用数组而不是对象存储，有利于数组序列化
            result.push（[decodeURIComponent（part [0]），decodeURIComponent（part [1]）]）;
        }
        返回结果;
    }

     // XMLHttpRequest Level 2文件上传（大礼帽提示为francois2metz）
    function fileUploadXhr（a）{
        var formdata = new FormData（）;

        for（var i = 0; i <a.length; i ++）{
            formdata.append（a [i] .name，a [i] .value）;
        }

        if（options.extraData）{
            var serializedData = deepSerialize（options.extraData）;
            for（i = 0; i <serializedData.length; i ++）{
                if（serializedData [i]）{
                    formdata.append（serializedData [i] [0]，serializedData [i] [1]）;
                }
            }
        }

        options.data = null;

        var s = $ .extend（true，{}，$ .ajaxSettings，options，{
            contentType：false，
            processData：false，
            cache：false，
            type：方法|| 'POST'
        }）;

        if（options.uploadProgress）{
            //解决方法因为jqXHR不公开upload属性
            s.xhr = function（）{
                var xhr = $ .ajaxSettings.xhr（）;
                if（xhr.upload）{
                    xhr.upload.addEventListener（'progress'，function（event）{
                        var percent = 0;
                        var position = event.loaded || event.position; 不推荐使用/*event.position * /
                        var total = event.total;
                        if（event.lengthComputable）{
                            ％= Math.ceil（位置/总数* 100）;
                        }
                        options.uploadProgress（event，position，total，percent）;
                    }，false）;
                }
                返回xhr;
            };
        }

        s.data = null;
        var beforeSend = s.beforeSend;
        s.beforeSend = function（xhr，o）{
            //发送用户提供的FormData（）
            if（options.formData）{
                o.data = options.formData;
            }
            其他{
                o.data = formdata;
            }
            if（beforeSend）{
                beforeSend.call（this，xhr，o）;
            }
        };
        return $ .ajax（s）;
    }

    //用于处理文件上传的私有函数（给YAHOO提示！）
    function fileUploadIframe（a）{
        var form = $ form [0]，el，i，s，g，id，$ io，io，xhr，sub，n，timedOut，timeoutHandle;
        var deferred = $ .Deferred（）;

        //＃341
        deferred.abort = function（status）{
            xhr.abort（状态）;
        };

        如果一个） {
            //确保仍然启用每个序列化输入
            for（i = 0; i <elements.length; i ++）{
                el = $（elements [i]）;
                if（hasProp）{
                    el.prop（'禁用'，假）;
                }
                其他{
                    el.removeAttr（ '禁用'）;
                }
            }
        }

        s = $ .extend（true，{}，$ .ajaxSettings，options）;
        s.context = s.context || S;
        id ='jqFormIO'+（new Date（）。getTime（））;
        if（s.iframeTarget）{
            $ io = $（s.iframeTarget）;
            n = $ io.attr2（'name'）;
            if（！n）{
                $ io.attr2（'name'，id）;
            }
            其他{
                id = n;
            }
        }
        其他{
            $ io = $（'<iframe name =“'+ id +'”src =“'+ s.iframeSrc +'”/>'）;
            $ io.css（{position：'absolute'，top：' -  1000px'，left：' -  1000px'}）;
        }
        io = $ io [0];


        xhr = {//模拟对象
            流产：0，
            responseText：null，
            responseXML：null，
            状态：0，
            statusText：'不适用'，
            getAllResponseHeaders：function（）{}，
            getResponseHeader：function（）{}，
            setRequestHeader：function（）{}，
            abort：function（status）{
                var e =（status ==='timeout'？'timeout'：'aborted'）;
                log（'aborting upload ...'+ e）;
                this.aborted = 1;

                试试{//＃214，＃257
                    if（io.contentWindow.document.execCommand）{
                        io.contentWindow.document.execCommand（ '停止'）;
                    }
                }
                catch（ignore）{}

                $ io.attr（'src'，s.iframeSrc）; // abort op正在进行中
                xhr.error = e;
                if（s.error）{
                    s.error.call（s.context，xhr，e，status）;
                }
                if（g）{
                    $ .event.trigger（“ajaxError”，[xhr，s，e]）;
                }
                if（s.complete）{
                    s.complete.call（s.context，xhr，e）;
                }
            }
        };

        g = s.global;
        //触发ajax全局事件，以便活动/块指示符正常工作
        if（g && 0 === $ .active ++）{
            $ .event.trigger（ “ajaxStart”）;
        }
        if（g）{
            $ .event.trigger（“ajaxSend”，[xhr，s]）;
        }

        if（s.beforeSend && s.beforeSend.call（s.context，xhr，s）=== false）{
            if（s.global）{
                $ .active--;
            }
            deferred.reject（）;
            退货延期;
        }
        if（xhr.aborted）{
            deferred.reject（）;
            退货延期;
        }

        //如果我们知道，请将提交元素添加到数据中
        sub = form.clk;
        if（sub）{
            n = sub.name;
            if（n &&！sub.disabled）{
                s.extraData = s.extraData || {};
                s.extraData [n] = sub.value;
                if（sub.type ==“image”）{
                    s.extraData [n +'。x'] = form.clk_x;
                    s.extraData [n +'。y'] = form.clk_y;
                }
            }
        }

        var CLIENT_TIMEOUT_ABORT = 1;
        var SERVER_ABORT = 2;
                
        function getDoc（frame）{
            / *它看起来像contentWindow或contentDocument
             *在ssl下运行时，在ie8中携带协议属性
             * frame.document是唯一有效的响应文档，因为
             *协议是已知的，但不是其他两个对象。奇怪？
             *“同源政策”http://en.wikipedia.org/wiki/Same_origin_policy
             * /
            
            var doc = null;
            
            // IE8级联访问检查
            尝试{
                if（frame.contentWindow）{
                    doc = frame.contentWindow.document;
                }
            } catch（err）{
                //在ssl和缺少协议下拒绝IE8访问
                log（'无法获取iframe.contentWindow文档：'+ err）;
            }

            if（doc）{//成功获取内容
                返回文档;
            }

            尝试{//只需检查可能会在ssl或不匹配协议下输入ie8
                doc = frame.contentDocument？frame.contentDocument：frame.document;
            } catch（err）{
                // 最后一次尝试
                log（'无法获取iframe.contentDocument：'+ err）;
                doc = frame.document;
            }
            返回文档;
        }

        // Rails CSRF hack（感谢Yvan Barthelemy）
        var csrf_token = $（'meta [name = csrf-token]'）。attr（'content'）;
        var csrf_param = $（'meta [name = csrf-param]'）。attr（'content'）;
        if（csrf_param && csrf_token）{
            s.extraData = s.extraData || {};
            s.extraData [csrf_param] = csrf_token;
        }

        //在上传开始之前，请等待重新粉刷获得一些CPU时间
        function doSubmit（）{
            //确保表格attrs已设定
            var t = $ form.attr2（'target'）， 
                a = $ form.attr2（'action'）， 
                mp ='multipart / form-data'，
                et = $ form.attr（'enctype'）|| $ form.attr（'encoding'）|| 熔点;

            //以IE友好的方式更新表单attrs
            form.setAttribute（ '目标'，ID）;
            if（！method || /post/i.test(method））{
                form.setAttribute（'method'，'POST'）;
            }
            if（a！= s.url）{
                form.setAttribute（'action'，s.url）;
            }

            //即在某些情况下设置编码时的borks
            if（！s.skipEncodingOverride &&（！method || /post/i.test(method）））{
                $ form.attr（{
                    编码：'multipart / form-data'，
                    enctype：'multipart / form-data'
                }）;
            }

            //支持timout
            if（s.timeout）{
                timeoutHandle = setTimeout（function（）{timedOut = true; cb（CLIENT_TIMEOUT_ABORT）;}，s.timeout）;
            }

            //寻找服务器中止
            function checkState（）{
                尝试{
                    var state = getDoc（io）.readyState;
                    log（'state ='+ state）;
                    if（state && state.toLowerCase（）=='uninitialized'）{
                        的setTimeout（checkState，50）;
                    }
                }
                catch（e）{
                    log（'Server abort：'，e，'（'，e.name，'）'）;
                    CB（SERVER_ABORT）;
                    if（timeoutHandle）{
                        clearTimeout（timeoutHandle）;
                    }
                    timeoutHandle = undefined;
                }
            }

            //如果在选项中提供，则添加“额外”数据
            var extraInputs = [];
            尝试{
                if（s.extraData）{
                    for（var.e in s.extraData）{
                        if（s.extraData.hasOwnProperty（n））{
                           //如果使用允许多个具有相同名称的值的$ .param格式
                           if（$。isPlainObject（s.extraData [n]）&& s.extraData [n] .hasOwnProperty（'name'）&& s.extraData [n] .hasOwnProperty（'value'））{
                               extraInputs.push（
                               $（'<input type =“hidden”name =“'+ s.extraData [n] .name +'”>'）。val（s.extraData [n] .value）
                                   .appendTo（形式）[0]）;
                           } else {
                               extraInputs.push（
                               $（'<input type =“hidden”name =“'+ n +'”>'）。val（s.extraData [n]）
                                   .appendTo（形式）[0]）;
                           }
                        }
                    }
                }

                if（！s.iframeTarget）{
                    //将iframe添加到doc并提交表单
                    $ io.appendTo（ '主体'）;
                }
                if（io.attachEvent）{
                    io.attachEvent（'onload'，cb）;
                }
                其他{
                    io.addEventListener（'load'，cb，false）;
                }
                的setTimeout（checkState，15）;

                尝试{
                    form.submit（）;
                } catch（err）{
                    //以防万一表单中包含名称/ id为'submit'的元素
                    var submitFn = document.createElement（'form'）。submit;
                    submitFn.apply（形式）;
                }
            }
            终于{
                //重置attrs并删除“额外”输入元素
                form.setAttribute（ '动作'，A）;
                form.setAttribute（'enctype'，et）; //＃380
                if（t）{
                    form.setAttribute（'target'，t）;
                } else {
                    $ form.removeAttr（ '目标'）;
                }
                $（extraInputs）一个.remove（）;
            }
        }

        if（s.forceSync）{
            doSubmit（）;
        }
        其他{
            setTimeout（doSubmit，10）; //这可以让dom更新呈现
        }

        var data，doc，domCheckCount = 50，callbackProcessed;

        function cb（e）{
            if（xhr.aborted || callbackProcessed）{
                返回;
            }
            
            doc = getDoc（io）;
            if（！doc）{
                log（'无法访问响应文档'）;
                e = SERVER_ABORT;
            }
            if（e === CLIENT_TIMEOUT_ABORT && xhr）{
                xhr.abort（ '超时'）;
                deferred.reject（xhr，'timeout'）;
                返回;
            }
            否则if（e == SERVER_ABORT && xhr）{
                xhr.abort（'server abort'）;
                deferred.reject（xhr，'error'，'server abort'）;
                返回;
            }

            if（！doc || doc.location.href == s.iframeSrc）{
                //尚未收到回复
                if（！timedOut）{
                    返回;
                }
            }
            if（io.detachEvent）{
                io.detachEvent（'onload'，cb）;
            }
            其他{
                io.removeEventListener（'load'，cb，false）;
            }

            var status ='success'，errMsg;
            尝试{
                if（timedOut）{
                    抛出'超时';
                }

                var isXml = s.dataType =='xml'|| doc.XMLDocument || $ .isXMLDoc（DOC）;
                日志（ 'isXml =' + isXml）;
                if（！isXml && window.opera &&（doc.body === null ||！doc.body.innerHTML））{
                    if（--domCheckCount）{
                        //在某些浏览器（Opera）中，iframe DOM并不总是可以遍历
                        // onload回调触发，所以我们循环一下以适应
                        log（'requeing onLoad callback，DOM not available'）;
                        setTimeout（cb，250）;
                        返回;
                    }
                    //让这个失败，因为服务器响应可能是一个空文档
                    // log（'多次尝试后无法访问iframe DOM'。）;
                    // throw'DOMException：not available';
                }

                // log（'检测到响应'）;
                var docRoot = doc.body？doc.body：doc.documentElement;
                xhr.responseText = docRoot？docRoot.innerHTML：null;
                xhr.responseXML = doc.XMLDocument？doc.XMLDocument：doc;
                if（isXml）{
                    s.dataType ='xml';
                }
                xhr.getResponseHeader = function（header）{
                    var headers = {'content-type'：s.dataType};
                    return headers [header.toLowerCase（）];
                };
                //支持XHR'status'和'statusText'仿真：
                if（docRoot）{
                    xhr.status = Number（docRoot.getAttribute（'status'））|| xhr.status;
                    xhr.statusText = docRoot.getAttribute（'statusText'）|| xhr.statusText;
                }

                var dt =（s.dataType ||''）。toLowerCase（）;
                var scr = /(json|script|text)/.test(dt）;
                if（scr || s.textarea）{
                    //查看用户是否在textarea中嵌入了响应
                    var ta = doc.getElementsByTagName（'textarea'）[0];
                    if（ta）{
                        xhr.responseText = ta.value;
                        //支持XHR'status'和'statusText'仿真：
                        xhr.status = Number（ta.getAttribute（'status'））|| xhr.status;
                        xhr.statusText = ta.getAttribute（'statusText'）|| xhr.statusText;
                    }
                    否则如果（scr）{
                        //注册浏览器围绕json响应的帐户
                        var pre = doc.getElementsByTagName（'pre'）[0];
                        var b = doc.getElementsByTagName（'body'）[0];
                        if（pre）{
                            xhr.responseText = pre.textContent？pre.textContent：pre.innerText;
                        }
                        否则如果（b）{
                            xhr.responseText = b.textContent？b.textContent：b.innerText;
                        }
                    }
                }
                else if（dt =='xml'&&！xhr.responseXML && xhr.responseText）{
                    xhr.responseXML = toXml（xhr.responseText）;
                }

                尝试{
                    data = httpData（xhr，dt，s）;
                }
                catch（错误）{
                    status ='parsererror';
                    xhr.error = errMsg =（err || status）;
                }
            }
            catch（错误）{
                log（'error caught：'，err）;
                status ='错误';
                xhr.error = errMsg =（err || status）;
            }

            if（xhr.aborted）{
                log（'upload aborted'）;
                status = null;
            }

            if（xhr.status）{//我们设置了xhr.status
                status =（xhr.status> = 200 && xhr.status <300 || xhr.status === 304）？'成功'：'错误';
            }

            //这些回调/触发器的排序很奇怪，但这就是$ .ajax的作用
            if（status ==='success'）{
                if（s.success）{
                    s.success.call（s.context，data，'success'，xhr）;
                }
                deferred.resolve（xhr.responseText，'success'，xhr）;
                if（g）{
                    $ .event.trigger（“ajaxSuccess”，[xhr，s]）;
                }
            }
            else if（status）{
                if（errMsg === undefined）{
                    errMsg = xhr.statusText;
                }
                if（s.error）{
                    s.error.call（s.context，xhr，status，errMsg）;
                }
                deferred.reject（xhr，'error'，errMsg）;
                if（g）{
                    $ .event.trigger（“ajaxError”，[xhr，s，errMsg]）;
                }
            }

            if（g）{
                $ .event.trigger（“ajaxComplete”，[xhr，s]）;
            }

            if（g &&！ -  $。active）{
                $ .event.trigger（ “ajaxStop”）;
            }

            if（s.complete）{
                s.complete.call（s.context，xhr，status）;
            }

            callbackProcessed = true;
            if（s.timeout）{
                clearTimeout（timeoutHandle）;
            }

            // 清理
            setTimeout（function（）{
                if（！s.iframeTarget）{
                    $ io.remove（）;
                }
                else {//添加其他来清理现有的iframe响应。
                    $ io.attr（'src'，s.iframeSrc）;
                }
                xhr.responseXML = null;
            }，100）;
        }

        var toXml = $ .parseXML || function（s，doc）{//使用parseXML（如果可用）（jQuery 1.5+）
            if（window.ActiveXObject）{
                doc = new ActiveXObject（'Microsoft.XMLDOM'）;
                doc.async ='false';
                doc.loadXML（一个或多个）;
            }
            其他{
                doc =（new DOMParser（））。parseFromString（s，'text / xml'）;
            }
            return（doc && doc.documentElement && doc.documentElement.nodeName！='parsererror'）？doc：null;
        };
        var parseJSON = $ .parseJSON || 功能） {
            / * jslint evil：true * /
            return window ['eval']（'（'+ s +'）'）;
        };

        var httpData = function（xhr，type，s）{//主要取自jq1.4.4

            var ct = xhr.getResponseHeader（'content-type'）|| '，
                xml = type ==='xml'|| ！type && ct.indexOf（'xml'）> = 0，
                data = xml？xhr.responseXML：xhr.responseText;

            if（xml && data.documentElement.nodeName ==='parsererror'）{
                if（$ .error）{
                    $ .error（ 'parsererror'）;
                }
            }
            if（s && s.dataFilter）{
                data = s.dataFilter（data，type）;
            }
            if（typeof data ==='string'）{
                if（type ==='json'||！type && ct.indexOf（'json'）> = 0）{
                    data = parseJSON（data）;
                } else if（type ===“script”||！type && ct.indexOf（“javascript”）> = 0）{
                    $ .globalEval（数据）;
                }
            }
            返回数据;
        };

        退货延期;
    }
};

/ **
 * ajaxForm（）提供了一种完全自动化表单提交的机制。
 *
 *使用此方法而不是ajaxSubmit（）的优点是：
 *
 * 1：此方法将包含<input type =“image”/>元素的坐标（如果元素
 *用于提交表格）。
 * 2.此方法将包含submit元素的名称/值数据（对于元素而言）
 *用于提交表格）。
 * 3.此方法将submit（）方法绑定到您的表单。
 *
 * ajaxForm的options参数与ajaxSubmit完全相同。ajaxForm只是
 *在正确绑定提交元素和事件的事件之后传递options参数
 *表格本身。
 * /
$ .fn.ajaxForm = function（options）{
    options = options || {};
    options.delegation = options.delegation && $ .isFunction（$。fn.on）;

    //在jQuery 1.3+中我们可以用就绪状态修复错误
    if（！options.delegation && this.length === 0）{
        var o = {s：this.selector，c：this.context};
        if（！$。isReady && os）{
            log（'DOM未准备好，排队ajaxForm'）;
            $（function（）{
                $（OS，OC）.ajaxForm（选件）;
            }）;
            归还这个;
        }
        //你的DOM准备好了吗？http://docs.jquery.com/Tutorials:Introducing_$（文件）。就绪（）
        log（'终止;由选择器找到的零元素'+（$ .isReady？''：'（DOM未准备好）'））;
        归还这个;
    }

    if（options.delegation）{
        $（文件）
            .off（'submit.form-plugin'，this.selector，doAjaxSubmit）
            .off（'click.form-plugin'，this.selector，captureSubmittingElement）
            .on（'submit.form-plugin'，this.selector，options，doAjaxSubmit）
            .on（'click.form-plugin'，this.selector，options，captureSubmittingElement）;
        归还这个;
    }

    return this.ajaxFormUnbind（）
        .bind（'submit.form-plugin'，options，doAjaxSubmit）
        .bind（'click.form-plugin'，options，captureSubmittingElement）;
};

//私有事件处理程序
function doAjaxSubmit（e）{
    / * jshint validthis：true * /
    var options = e.data;
    if（！e.isDefaultPrevented（））{//如果事件已被取消，请不要继续
        e.preventDefault（）;
        $（e.target）.ajaxSubmit（选件）; //＃365
    }
}

function captureSubmittingElement（e）{
    / * jshint validthis：true * /
    var target = e.target;
    var $ el = $（target）;
    if（！（$ el.is（“[type = submit]，[type = image]”）））{
        //这是提交文件的子元素吗？（例如：按钮内的跨度）
        var t = $ el.closest（'[type = submit]'）;
        if（t.length === 0）{
            返回;
        }
        target = t [0];
    }
    var form = this;
    form.clk = target;
    if（target.type =='image'）{
        if（e.offsetX！== undefined）{
            form.clk_x = e.offsetX;
            form.clk_y = e.offsetY;
        } else if（typeof $ .fn.offset =='function'）{
            var offset = $ el.offset（）;
            form.clk_x = e.pageX  -  offset.left;
            form.clk_y = e.pageY  -  offset.top;
        } else {
            form.clk_x = e.pageX  -  target.offsetLeft;
            form.clk_y = e.pageY  -  target.offsetTop;
        }
    }
    //清楚形式的变种
    setTimeout（function（）{form.clk = form.clk_x = form.clk_y = null;}，100）;
}


// ajaxFormUnbind解除绑定由ajaxForm绑定的事件处理程序
$ .fn.ajaxFormUnbind = function（）{
    返回this.unbind（'submit.form-plugin click.form-plugin'）;
};

/ **
 * formToArray（）将表单元素数据收集到可以的对象数组中
 *传递给以下任何ajax函数：$ .get，$ .post或load。
 *数组中的每个对象都具有“名称”和“值”属性。一个例子
 *简单登录表单的数组可能是：
 *
 * [{name：'username'，value：'jresig'}，{name：'password'，value：'secret'}]
 *
 *这个数组被传递给提供给它的预提交回调函数
 * ajaxSubmit（）和ajaxForm（）方法。
 * /
$ .fn.formToArray = function（语义，元素）{
    var a = [];
    if（this.length === 0）{
        返回;
    }

    var form = this [0];
    var formId = this.attr（'id'）;
    var els =语义？form.getElementsByTagName（'*'）：form.elements;
    var els2;

    if（els &&！/ MSIE [678] / .test（navigator.userAgent））{//＃390
        els = $（els）.get（）; //转换为标准数组
    }

    //＃386; 在表单之外输入使用'form'属性的帐户
    if（formId）{
        els2 = $（'：input [form =“'+ formId +'”]'）。get（）; //帽子小贴士@thet
        if（els2.length）{
            els =（els || []）。concat（els2）;
        }
    }

    if（！els ||！els.length）{
        返回;
    }

    var i，j，n，v，el，max，jmax;
    for（i = 0，max = els.length; i <max; i ++）{
        el = els [i];
        n = el.name;
        if（！n || el.disabled）{
            继续;
        }

        if（semantic && form.clk && el.type ==“image”）{
            //当语义== true时，动态处理图像输入
            if（form.clk == el）{
                a.push（{name：n，value：$（el）.val（），type：el.type}）;
                a.push（{name：n +'。x'，value：form.clk_x}，{name：n +'。y'，value：form.clk_y}）;
            }
            继续;
        }

        v = $ .fieldValue（el，true）;
        if（v && v.constructor == Array）{
            if（elements）{
                elements.push（EL）;
            }
            for（j = 0，jmax = v.length; j <jmax; j ++）{
                a.push（{name：n，value：v [j]}）;
            }
        }
        else if（feature.fileapi && el.type =='file'）{
            if（elements）{
                elements.push（EL）;
            }
            var files = el.files;
            if（files.length）{
                for（j = 0; j <files.length; j ++）{
                    a.push（{name：n，value：files [j]，type：el.type}）;
                }
            }
            其他{
                //＃180
                a.push（{name：n，value：''，type：el.type}）;
            }
        }
        否则if（v！== null && typeof v！='undefined'）{
            if（elements）{
                elements.push（EL）;
            }
            a.push（{name：n，value：v，type：el.type，required：el.required}）;
        }
    }

    if（！semantic && form.clk）{
        //在element数组中找不到输入类型=='图像'！在这里处理它
        var $ input = $（form.clk），input = $ input [0];
        n = input.name;
        if（n &&！input.disabled && input.type =='image'）{
            a.push（{name：n，value：$ input.val（）}）;
            a.push（{name：n +'。x'，value：form.clk_x}，{name：n +'。y'，value：form.clk_y}）;
        }
    }
    返回;
};

/ **
 *将表单数据序列化为'submittable'字符串。此方法将返回一个字符串
 *格式为：name1 = value1＆amp; name2 = value2
 * /
$ .fn.formSerialize = function（semantic）{
    //切换到jQuery.param以获得正确的编码
    return $ .param（this.formToArray（semantic））;
};

/ **
 *将jQuery对象中的所有字段元素序列化为查询字符串。
 *此方法将返回以下格式的字符串：name1 = value1＆amp; name2 = value2
 * /
$ .fn.fieldSerialize = function（successful）{
    var a = [];
    this.each（function（）{
        var n = this.name;
        if（！n）{
            返回;
        }
        var v = $ .fieldValue（this，successful）;
        if（v && v.constructor == Array）{
            for（var i = 0，max = v.length; i <max; i ++）{
                a.push（{name：n，value：v [i]}）;
            }
        }
        否则if（v！== null && typeof v！='undefined'）{
            a.push（{name：this.name，value：v}）;
        }
    }）;
    //切换到jQuery.param以获得正确的编码
    返回$ .param（a）;
};

/ **
 *返回匹配集中元素的值。例如，请考虑以下形式：
 *
 * <form> <fieldset>
 * <input name =“A”type =“text”/>
 * <input name =“A”type =“text”/>
 * <输入名称=“B”类型=“复选框”值=“B1”/>
 * <输入名称=“B”类型=“复选框”值=“B2”/>
 * <input name =“C”type =“radio”value =“C1”/>
 * <input name =“C”type =“radio”value =“C2”/>
 * </ fieldset> </ form>
 *
 * var v = $（'input [type = text]'）。fieldValue（）;
 * //如果没有在文本输入中输入值
 * v == [''，'']
 * //如果在文本输入中输入的值是'foo'和'bar'
 * v == ['foo'，'bar']
 *
 * var v = $（'input [type = checkbox]'）。fieldValue（）;
 * //如果没有选中复选框
 * v === undefined
 * //如果选中了两个复选框
 * v == ['B1'，'B2']
 *
 * var v = $（'input [type = radio]'）。fieldValue（）;
 * //如果没有检查无线电
 * v === undefined
 * //如果检查第一个无线电
 * v == ['C1']
 *
 *成功的参数控制字段元素是否必须“成功”
 *（根据http://www.w3.org/TR/html4/interact/forms.html#successful-controls）。
 *成功参数的默认值为true。如果此值为false，则值为s
 *返回每个元素。
 *
 *注意：此方法*始终*返回一个数组。如果没有有效值可以确定
 *数组将为空，否则将包含一个或多个值。
 * /
$ .fn.fieldValue = function（success）{
    for（var val = []，i = 0，max = this.length; i <max; i ++）{
        var el = this [i];
        var v = $ .fieldValue（el，success）;
        if（v === null || typeof v =='undefined'||（v.constructor == Array &&！v.length））{
            继续;
        }
        if（v.constructor == Array）{
            $ .merge（val，v）;
        }
        其他{
            val.push（V）;
        }
    }
    返回;
};

/ **
 *返回field元素的值。
 * /
$ .fieldValue = function（el，success）{
    var n = el.name，t = el.type，tag = el.tagName.toLowerCase（）;
    if（成功=== undefined）{
        successful = true;
    }

    if（成功&&（！n || el.disabled || t =='reset'|| t =='button'||
        （t =='复选框'|| t =='radio'）&&！el.checked ||
        （t =='submit'|| t =='image'）&& el.form && el.form.clk！= el ||
        tag =='select'&& el.selectedIndex == -1））{
            return null;
    }

    if（tag =='select'）{
        var index = el.selectedIndex;
        if（index <0）{
            return null;
        }
        var a = []，ops = el.options;
        var one =（t =='select-one'）;
        var max =（one？index + 1：ops.length）;
        for（var i =（one？index：0）; i <max; i ++）{
            var op = ops [i];
            if（op.selected）{
                var v = op.value;
                if（！v）{// IE的额外痛苦......
                    v =（op.attributes && op.attributes.value &&！（op.attributes.value.specified））？op.text：op.value;
                }
                如果一个） {
                    返回v;
                }
                a.push（V）;
            }
        }
        返回;
    }
    return $（el）.val（）;
};

/ **
 *清除表单数据。对表单的输入字段执行以下操作：
 *  - 输入文本字段的'value'属性设置为空字符串
 *  -  select元素的'selectedIndex'属性设置为-1
 *  - 复选框和无线电输入将其“已检查”属性设置为false
 *  - 类型提交，按钮，重置和隐藏的输入*不会生效
 *  - 按钮元素将不会生效
 * /
$ .fn.clearForm = function（includeHidden）{
    return this.each（function（）{
        $（'input，select，textarea'，this）.clearFields（includeHidden）;
    }）;
};

/ **
 *清除选定的表单元素。
 * /
$ .fn.clearFields = $ .fn.clearInputs = function（includeHidden）{
    var re = / ^（?: color | date | datetime | email | month | number | password | range | search | tel | text | time | url | week）$ / i; //'隐藏'不在此列表中
    return this.each（function（）{
        var t = this.type，tag = this.tagName.toLowerCase（）;
        if（re.test（t）|| tag =='textarea'）{
            this.value ='';
        }
        否则if（t =='复选框'|| t =='radio'）{
            this.checked = false;
        }
        else if（tag =='select'）{
            this.selectedIndex = -1;
        }
        否则if（t ==“file”）{
            if（/MSIE/.test(navigator.userAgent））{
                $（本）.replaceWith（$（本）.clone（真））;
            } else {
                $（本）.VAL（ ''）;
            }
        }
        else if（includeHidden）{
            // includeHidden可以是值true，也可以是选择器字符串
            //表示特殊测试; 例如：
            // $（'＃myForm'）。clearForm（'。special：hidden'）
            //以上内容会清除隐藏的输入，这些输入具有“特殊”类
            if（（includeHidden === true && /hidden/.test(t））||
                 （typeof includeHidden =='string'&& $（this）.is（includeHidden）））{
                this.value ='';
            }
        }
    }）;
};

/ **
 *重置表单数据。导致所有表单元素重置为其原始值。
 * /
$ .fn.resetForm = function（）{
    return this.each（function（）{
        //防止名为“重置”的输入
        //请注意，IE将重置功能报告为“对象”
        if（typeof this.reset =='function'||（typeof this.reset =='object'&&！this.reset.nodeType））{
            this.reset（）;
        }
    }）;
};

/ **
 *启用或禁用任何匹配元素。
 * /
$ .fn.enable = function（b）{
    if（b === undefined）{
        b =真;
    }
    return this.each（function（）{
        this.disabled =！b;
    }）;
};

/ **
 *检查/取消选中任何匹配的复选框或单选按钮
 *选择/取消选择和匹配选项元素。
 * /
$ .fn.selected = function（select）{
    if（select === undefined）{
        select = true;
    }
    return this.each（function（）{
        var t = this.type;
        if（t =='checkbox'|| t =='radio'）{
            this.checked = select;
        }
        else if（this.tagName.toLowerCase（）=='option'）{
            var $ sel = $（this）.parent（'select'）;
            if（select && $ sel [0] && $ sel [0] .type =='select-one'）{
                //取消选择所有其他选项
                。$ sel.find（ '选项'）选择（假）;
            }
            this.selected = select;
        }
    }）;
};

//公开调试var
$ .fn.ajaxSubmit.debug = false;

// helper fn用于控制台日志记录
function log（）{
    if（！$。fn.ajaxSubmit.debug）{
        返回;
    }
    var msg ='[jquery.form]'+ Array.prototype.join.call（arguments，''）;
    if（window.console && window.console.log）{
        window.console.log（MSG）;
    }
    else if（window.opera && window.opera.postError）{
        window.opera.postError（MSG）;
    }
}

}））;