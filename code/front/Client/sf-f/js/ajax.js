
  	function myAjax(url,postData,success,error){
            // 此种方式不会 中文乱码,
            var type = postData.type;
            var timeout = postData.timeout;
            var data = postData.data;
            var xhr = new plus.net.XMLHttpRequest();
            if(timeout&&timeout>0) xhr.timeout = timeout;
            xhr.onreadystatechange = function () {
                switch ( xhr.readyState ) {
                    case 0:
                    break;
                    case 1:
                    break;
                    case 2:
                    break;
                    case 3:
                        break;
                    case 4:
                        if ( xhr.status == 200 ) {
                            success(eval('('+xhr.responseText+')'));
                        } else {
                            error(xhr.readyState,xhr);
                        }
                        break;
                    default :
                        break;
                }
            }
            if(data){
                if(type=='post'||type=='get'){
                    xhr.open( type||"GET", url );
                    xhr.send(JSON.stringify(data));
                }else{
                    throw new Error("type is undefined !")
                }
            }else{
                if(type!='post'&&type!='get'){
                    throw new Error("type is undefined !")
                }
                xhr.open( type||"GET", url );
                xhr.send();
            }
        }
