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
    var content = $("#input-" + commentId).val();
    window.localStorage.setItem("openComment2-id",commentId);
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
        },
        dataType:"json"
    })
}


/**
 * 展开二级评论
 * */
function collapseComments(e){
    var id = e.getAttribute("data-id");
    var comments = $("#comment-" + id);

    // 获取一下二级评论的展开状态
    var collapse = e.getAttribute("data-collapse");
    if (collapse) {
        // 折叠二级评论
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    } else {
        var subCommentContainer = $("#comment-" + id);
        if (subCommentContainer.children().length != 1) {
            //展开二级评论
            comments.addClass("in");
            // 标记二级评论展开状态
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        } else {
            $.getJSON("/comment/" + id, function (data) {
                $.each(data.data.reverse(), function (index, comment) {
                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    }).append($("<img/>", {
                        "class": "media-object img-rounded",
                        "src": comment.users.avatarUrl
                    }));

                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.users.name
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "menu"
                    }).append($("<span/>", {
                        "class": "pull-right",
                        "html": moment(comment.gmtCreate).format('YYYY-MM-DD')
                    })));

                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement).append(mediaBodyElement);

                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments"
                    }).append(mediaElement);

                    subCommentContainer.prepend(commentElement);
                });
                //展开二级评论
                comments.addClass("in");
                // 标记二级评论展开状态
                e.setAttribute("data-collapse", "in");
                e.classList.add("active");
            });
        }
    }
}

/**
 * 点赞按钮
 * */
function likeComments(e){
    var id = e.getAttribute("data-id");
    var heart_icon = $("#comment-heart-icon-" + id);
    console.log(id);
    var like = e.getAttribute("like","yes");
    if(like){
        //取消点赞
        heart_icon.removeClass("glyphicon-heart");
        heart_icon.addClass("glyphicon-heart-empty");
        e.removeAttribute("like");
        e.classList.remove("like");
        //调用API进行数据库操作
        $.ajax({
            type:"POST",
            url:"/comment/like",
            contentType:"application/json",
            data:JSON.stringify({
                "id":id,
                "isLike":0
            }),
            success:function (response){
                if(response.code != 200){
                    alert(response.message);
                }else{

                }
            },
            dataType:"json"
        })
    }else{
        //点赞
        heart_icon.removeClass("glyphicon-heart-empty");
        heart_icon.addClass("glyphicon-heart");
        e.setAttribute("like","yes");
        e.classList.add("like");
        //调用API进行数据库操作
        $.ajax({
            type:"POST",
            url:"/comment/like",
            contentType:"application/json",
            data:JSON.stringify({
                "id":id,
                "isLike":1
            }),
            success:function (response){
                if(response.code != 200){
                    alert(response.message);
                }else{

                }
            },
            dataType:"json"
        })
    }
}


/**
 * 选择标签
 * */
function selectTag(e){
    var value = e.getAttribute("data-tag");
    var previous = $("#tag").val();
    if(previous.indexOf(value) == -1){
        if(previous){
            $("#tag").val(previous + ',' + value);
        }else{
            $("#tag").val(value);
        }
    }
}

/**
 * 展示所有可选择的标签
 * */
function showSelectTag(){
    $("#select-tag").show();
}

