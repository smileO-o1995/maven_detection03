
$(function(){
	// 初始化插件
	$("#myupload").zyUpload({
		width            :   "450px",                 // 宽度
		height           :   "200px",                 // 宽度
		itemWidth        :   "120px",                 // 文件项的宽度
		itemHeight       :   "90px",                 // 文件项的高度  正确地址为http://localhost:8889/maven_detection03/testServlet
		url              :   "uploadFileServlet2",  // 上传文件的路径(这里上传路径存在bug，只能在webapp的表层)
		fileType         :   ["v"],// 上传文件的类型
		fileSize         :   1024 * 1024 * 20,                // 上传文件的大小
        fileLength       :   10,                       // 上传文件的个数
        everyLength      :   1,                       // 每一次上传文件的个数
		multiple         :   false,                    // 是否可以多个文件上传
		dragDrop         :   true,                    // 是否可以拖动上传文件
		del              :   true,                    // 是否可以删除文件
		finishDel        :   true,  				  // 是否在上传文件完成后删除预览
		/* 外部获得的回调接口 */
		onSelect: function(selectFiles, allFiles){    // 选择文件的回调方法  selectFile:当前选中的文件  allFiles:还没上传的全部文件
			console.info("当前选择了以下文件：");
			console.info(selectFiles);
		},
		onDelete: function(file, files){              // 删除一个文件的回调方法 file:当前删除的文件  files:删除之后的文件
			console.info("当前删除了此文件：");
			console.info(file.name);
		},
		onSuccess: function(file, rtnData){          // 文件上传成功的回调方法
			console.info("此文件上传成功：");
            //上传成功后回调函数
            /*
            1、先将数据转换为json格式
            2、显示需要打印“测试结果”区域的代码
             */
            var obj;
            //判断是否为json格式
            if((typeof rtnData=='object')&&rtnData.constructor==Object){
                obj=rtnData;
            }else{
                obj  = eval("("+rtnData+")");
            }
            $(".rstCardJs").show();
            $(".netTitleJs").append(obj.fileName);
            $(".step1_2CommitBtn").append(obj.fileName);

		},
		onFailure: function(file, response){          // 文件上传失败的回调方法
			console.info("此文件上传失败：");
			console.info(file.name);
		},
		onComplete: function(response){           	  // 上传完成的回调方法 response为返回的结果集
			console.info("文件上传完成");
			console.info(response);
		}
	});
	
});

