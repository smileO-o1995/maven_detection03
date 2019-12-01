// function exportPdf(){
//     var target = document.getElementById("exportTable");//要转化页面内容的id
//     target.style.background = "#FFFFFF";
//     html2canvas(target, {
//         onrendered:function(canvas) {
//             //渲染完成时调用的
//             var contentWidth = canvas.width;
//             var contentHeight = canvas.height;
//
//             //一页pdf显示html页面生成的canvas高度;
//             var pageHeight = contentWidth / 592.28 * 841.89;
//             //未生成pdf的html页面高度
//             var leftHeight = contentHeight;
//             //页面偏移
//             var position = 0;
//             //a4纸的尺寸[595.28,841.89]，html页面生成的canvas在pdf中图片的宽高
//             var imgWidth = 841.89;
//             var imgHeight = 841.89/contentWidth * contentHeight;
//
//             var pageData = canvas.toDataURL('image/jpeg', 1.0);
//
//             var pdf = new jsPDF('l', 'pt', 'a4');  //l：横向  p：纵向
//
//             //有两个高度需要区分，一个是html页面的实际高度，和生成pdf的页面高度(595.28)
//             //当内容未超过pdf一页显示的范围，无需分页
//             if (leftHeight < pageHeight) {
//                 pdf.addImage(pageData, 'JPEG', 26, 43, imgWidth, imgHeight );
//             } else {
//                 while(leftHeight > 0) {
//                     pdf.addImage(pageData, 'JPEG', 0, position, imgWidth, imgHeight);
//                     leftHeight -= pageHeight;
//                     position -= 595.28;
//                     //避免添加空白页
//                     if(leftHeight > 0) {
//                         pdf.addPage();
//                     }
//                 }
//             }
//             pdf.save($(".netTitleJs").html() + "诊断结果.pdf");
//         }
//     });
// }

function exportPdf() {
    var target = document.getElementById("exportTable");//要转化页面内容的id
    target.style.background = "#FFFFFF";
    html2canvas(target, {
        onrendered:function(canvas) {
            var contentWidth = canvas.width;
            var contentHeight = canvas.height;
            //一页pdf显示html页面生成的canvas高度;
            var pageHeight = contentWidth / 595.28 * 841.89;
            //未生成pdf的html页面高度
            var leftHeight = contentHeight;
            //pdf页面偏移
            var position = 0;
            //a4纸的尺寸[595.28,841.89]，html页面生成的canvas在pdf中图片的宽高
            var imgWidth = 555.28;
            var imgHeight = 555.28/contentWidth * contentHeight;
            var pageData = canvas.toDataURL('image/jpeg', 1.0);
            var pdf = new jsPDF('', 'pt', 'a4');
            //有两个高度需要区分，一个是html页面的实际高度，和生成pdf的页面高度(841.89)
            //当内容未超过pdf一页显示的范围，无需分页
            if (leftHeight < pageHeight) {
                pdf.addImage(pageData, 'JPEG', 20, 0, imgWidth, imgHeight );
            } else {
                while(leftHeight > 0) {
                    pdf.addImage(pageData, 'JPEG', 20, position, imgWidth, imgHeight)
                    leftHeight -= pageHeight;
                    position -= 841.89;
                    //避免添加空白页
                    if(leftHeight > 0) {
                        pdf.addPage();
                    }
                }
            }
            pdf.save($(".netTitleJs").html() + "诊断结果.pdf");
        }
    })
}