/**
 * 提交一级评论 并刷新评论数据
 * */
function post(){
    var questionId = $("#question_id").val();
    var commentContent = $("#comment_content").val();
    comment2target(questionId,1,commentContent);
}

/**
 * 提交二级评论 并刷新评论数据
 * */
function comment(e){
    var commentId = e.getAttribute("data-id");
    console.log(commentId)
    var content = $("#input-" + commentId).val();
    comment2target(commentId,2,content);
}

/**
 * 提交回复数据的API 提交成功会自动刷新
 * */
function comment2target(targetId,type,content){
    if(!content){
        alert("回复内容为空，请重新输入！");
        return ;
    }
    $.ajax({
        type:"POST",
        url:"/comment",
        contentType:"application/json",
        data:JSON.stringify({
            "parentId":targetId,
            "content":content,
            "commentType":type
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


/**
 * 展开二级评论
 * */
function collapseComments(e){
    var id = e.getAttribute("data-id");
    var comments = $("#comment-"+id);
    //获取二级评论的展开状态
    var collapse = e.getAttribute("data-collapse");
    if(collapse){
        //折叠耳机评论
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        //不显示icon高亮
        e.classList.remove("active");
    }else{
        //获取二级评论数据
        $.getJSON( "/comment/" + id, function( data ) {

            // $.each( data, function( key, val ) {
            //     items.push( "<li id='" + key + "'>" + val + "</li>" );
            // });
            //
            // $( "<ul/>", {
            //     "class": "my-new-list",
            //     html: items.join( "" )
            // }).appendTo( "body" );
            //展开二级评论
            comments.addClass("in");
            e.setAttribute("data-collapse","in");
            //显示icon高亮
            e.classList.add("active");
        });
    }
}