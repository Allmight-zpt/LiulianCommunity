var restTime = 5;
var time = restTime*1000;//计算出毫秒数
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
                    //33 左外眼睑  398 右外眼睑 详见：https://blog.csdn.net/FL1623863129/article/details/126783864
                    //左右转头的逻辑：上述两个点的z值一正一负即可
                    //上下转头的逻辑：另外找两个点在脸上一上一下的位置
                    //待解决：画面左右翻转问题
                    // console.log("left")
                    // console.log(landmarks[33]);
                    // console.log("right");
                    // console.log(landmarks[398]);
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
        }
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