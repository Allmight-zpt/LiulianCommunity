var restTime = 60 * 59;//肩颈活动提示倒计时
var time = restTime*1000;//计算出毫秒数
var percent = 0;//肩颈活动完成进度
var finish = false;//是否完成肩颈活动
var finishRestTime = 5;//完成后自动关闭倒计时
/**
 * 头部方向
 * 0: 面向屏幕
 * 1: 向左
 * 2: 向右
 * 3: 向上
 * 4: 向下
 * */
var lastHeadDirection = 0;//上一帧头部方向
var headDirection = 0;//当前帧头部方向

setInterval('t_time()',1000);
function t_time() {
    //倒计时
    var mm = parseInt(time/60/1000%60,10);//剩余的分钟数
    var ss = parseInt(time/1000%60,10);//剩余的秒数
    time = time-1000;
    if(mm==0 && ss==0) {
        $("#myEnd").modal("toggle");
        const videoElement = document.getElementsByClassName('input_video')[0];
        const canvasElement = document.getElementsByClassName('output_canvas')[0];
        const canvasCtx = canvasElement.getContext('2d');
        function onResults(results) {
            canvasCtx.save();
            canvasCtx.clearRect(0, 0, canvasElement.width, canvasElement.height);
            canvasCtx.drawImage(results.image, 0, 0, canvasElement.width, canvasElement.height);
            if (results.multiFaceLandmarks) {
                for (const landmarks of results.multiFaceLandmarks) {
                    //四个关键点确定头部转动方向：33 左外眼睑 398 右外眼睑 68 眉心 164 人中（各点位置详见：https://blog.csdn.net/FL1623863129/article/details/126783864）
                    //1. 使用z在x方向上的偏导数判断左右转头
                    var derivativeX = (landmarks[33].z - landmarks[398].z) / (landmarks[33].x - landmarks[398].x);
                    //2. 使用z在y方向上的偏导数判断上下点头
                    var derivativeY = (landmarks[68].z - landmarks[164].z) / (landmarks[68].y - landmarks[164].y);
                    //3. 确定头部状态
                    //3.1 向上活动 derivativeX大于阈值1.1
                    if(derivativeX > 1.1){
                        lastHeadDirection = headDirection;
                        headDirection = 1;
                    }
                    //3.2 向下活动 derivativeX小于阈值-1.1
                    else if(derivativeX < -1.1){
                        lastHeadDirection = headDirection;
                        headDirection = 2;
                    }
                    //3.3 向左活动 derivativeY小于阈值-1
                    else if(derivativeY < -1){
                        lastHeadDirection = headDirection;
                        headDirection = 3;
                    }
                    //3.4 向右活动 derivativeY大于阈值0.2
                    else if(derivativeY > 0.2){
                        lastHeadDirection = headDirection;
                        headDirection = 4;
                    }
                    //3.5 面向屏幕
                    else{
                        lastHeadDirection = headDirection;
                        headDirection = 0;
                    }
                    //4. 根据相邻帧关头部状态确定头部运动方向
                    if(headDirection == 0 && lastHeadDirection == 1){
                        percent++;
                        console.log("left");
                    }
                    else if(headDirection == 0 && lastHeadDirection == 2){
                        percent++;
                        console.log("right");
                    }
                    else if(headDirection == 0 && lastHeadDirection == 3){
                        percent++;
                        console.log("up");
                    }
                    else if(headDirection == 0 && lastHeadDirection == 4){
                        percent++;
                        console.log("down");
                    }
                    //绘制关键点
                    drawConnectors(canvasCtx, landmarks, FACEMESH_TESSELATION, {color: '#C0C0C070', lineWidth: 1});
                    drawConnectors(canvasCtx, landmarks, FACEMESH_RIGHT_EYE, {color: '#FF3030'});
                    drawConnectors(canvasCtx, landmarks, FACEMESH_RIGHT_EYEBROW, {color: '#FF3030'});
                    drawConnectors(canvasCtx, landmarks, FACEMESH_RIGHT_IRIS, {color: '#FF3030'});
                    drawConnectors(canvasCtx, landmarks, FACEMESH_LEFT_EYE, {color: '#30FF30'});
                    drawConnectors(canvasCtx, landmarks, FACEMESH_LEFT_EYEBROW, {color: '#30FF30'});
                    drawConnectors(canvasCtx, landmarks, FACEMESH_LEFT_IRIS, {color: '#30FF30'});
                    drawConnectors(canvasCtx, landmarks, FACEMESH_FACE_OVAL, {color: '#E0E0E0'});
                    drawConnectors(canvasCtx, landmarks, FACEMESH_LIPS, {color: '#E0E0E0'});
                }
            }
            canvasCtx.restore();
            // 修改活动完成进度条
            if(finish == false){
                var realPercent = percent * 12.5;
                document.getElementById("complete-percent").innerHTML = realPercent + "";
                document.getElementById("progress-bar").style.width = realPercent + "%";
            }
            // 进度到达100%时
            if(realPercent == 100){
                finish = true;
                console.log("finish");
                /**
                 * 1. 显示成功界面
                 * 2. 显示倒计时 5 秒后自动关闭界面
                 * 3. 倒计时 5 秒后关闭
                 * */
                document.getElementById("modal-body").innerHTML = "<div class='finish-word'>完成肩颈运动，眺望远方让眼睛也休息一下吧~</div>" +
                                                                            "<br>" +
                                                                            "<div id='finish-countDown' class='finish-countDown'></div>"
                setInterval(function (){
                    document.getElementById("finish-countDown").innerHTML = "<font style='color:#d9534f'>" + finishRestTime + "</font>" + " 秒后自动关闭";
                    finishRestTime--;
                },1000);
                setTimeout(function (){
                    location.reload();
                },6000);
            }
        }
        // 脸部检测
        const faceMesh = new FaceMesh({locateFile: (file) => {
                return `https://cdn.jsdelivr.net/npm/@mediapipe/face_mesh/${file}`;
            }});
        faceMesh.setOptions({
            maxNumFaces: 1,
            refineLandmarks: true,
            minDetectionConfidence: 0.5,
            minTrackingConfidence: 0.5
        });
        faceMesh.onResults(onResults);
        const camera = new Camera(videoElement, {
            onFrame: async () => {
                await faceMesh.send({image: videoElement});
            }
        });
        camera.start();
    }
}