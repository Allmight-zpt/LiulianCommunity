function post(){
    var questionId = $("#question_id").val();
    var commentContent = $("#comment_content").val();
    if(!commentContent){
        alert("回复内容为空，请重新输入！");
        return ;
    }
    $.ajax({
        type:"POST",
        url:"/comment",
        contentType:"application/json",
        data:JSON.stringify({
            "parentId":questionId,
            "content":commentContent,
            "commentType":1
        }),
        success:function (response){
            if(response.code == 200){
                window.location.reload();
            }else{
                if(response.code == 2003){
                    var isAccepted = confirm(response.message);
                    if(isAccepted){
                        window.open("https://gitee.com/oauth/authorize?client_id=3c00200aa16ff49ec710494ac362251d532fc7eb056fcf4e85f6783ae7f45af3&redirect_uri=http://localhost:8887/callback&response_type=code")
                        //让跳转的登录页面自动关闭
                        window.localStorage.setItem("closeable",true);
                    }
                }else{
                    alert(response.message);
                }
            }
            console.log(response);
        },
        dataType:"json"
    })
}