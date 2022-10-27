function siteTime(){
    window.setTimeout("siteTime()", 1000);
    var seconds = 1000
    var minutes = seconds * 60
    var hours = minutes * 60
    var days = hours * 24
    var years = days * 365
    var today = new Date()
    var todayYear = today.getFullYear()
    var todayMonth = today.getMonth() + 1
    var todayDate = today.getDate()
    var todayHour = today.getHours()
    var todayMinute = today.getMinutes()
    var todaySecond = today.getSeconds()
    var t1 = Date.UTC(2022,10,11,9,0,0)
    var t2 = Date.UTC(todayYear,todayMonth,todayDate,todayHour,todayMinute,todaySecond)
    var diff = t2-t1
    var diffYears = Math.floor(diff/years)
    var diffDays = Math.floor((diff/days)-diffYears*365)
    var diffHours = Math.floor((diff-(diffYears*365+diffDays)*days)/hours)
    var diffMinutes = Math.floor((diff-(diffYears*365+diffDays)*days-diffHours*hours)/minutes)
    var diffSeconds = Math.floor((diff-(diffYears*365+diffDays)*days-diffHours*hours-diffMinutes*minutes)/seconds)
    html = ""
    if(diffYears>0){
        html += "<font style='color:#c40000'>" + diffYears +"</font>" + " 年 "
    }
    if(diffDays>0){
        html += "<font style='color:#c40000'>" + diffDays +"</font>" + " 天 "
    }
    if(diffHours>0){
        html += "<font style='color:#c40000'>" + diffHours +"</font>" + " 小时 "
    }
    if(diffMinutes>0){
        html += "<font style='color:#c40000'>" + diffMinutes +"</font>" + " 分钟 "
    }
    if(diffSeconds>0){
        html += "<font style='color:#c40000'>" + diffSeconds +"</font>" + " 秒 "
    }
    document.getElementById("sitetime").innerHTML=html
}
siteTime();