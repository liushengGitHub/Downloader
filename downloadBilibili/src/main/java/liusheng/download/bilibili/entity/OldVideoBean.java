package liusheng.download.bilibili.entity;

import liusheng.downloadCore.entity.AbstractDataBean;

import java.util.List;

public class OldVideoBean extends AbstractDataBean {

    /**
     * code : 0
     * message : 0
     * ttl : 1
     * data : {"from":"local","result":"suee","message":"","quality":64,"format":"flv720","timelength":3786344,"accept_format":"flv,flv720,flv480,flv360","accept_description":["高清 1080P","高清 720P","清晰 480P","流畅 360P"],"accept_quality":[80,64,32,16],"video_codecid":7,"seek_param":"start","seek_type":"offset","durl":[{"order":1,"length":261536,"size":63769493,"ahead":"EhA=","vhead":"AWQAH//hABpnZAAfrNlAUAW7ARAAAAMAEAAAAwMg8YMZYAEABWjr7PI8","url":"http://upos-hz-mirrorcosu.acgvideo.com/upgcxcode/67/96/46919667/46919667-1-64.flv?e=ig8euxZM2rNcNbTV7zUVhoMjhwuBhwdEto8g5X10ugNcXBlqNxHxNEVE5XREto8KqJZHUa6m5J0SqE85tZvEuENvNC8xNEVE9EKE9IMvXBvE2ENvNCImNEVEK9GVqJIwqa80WXIekXRE9IMvXBvEuENvNCImNEVEua6m2jIxux0CkF6s2JZv5x0DQJZY2F8SkXKE9IB5QK==&deadline=1560489850&gen=playurl&nbs=1&oi=2026550635&os=cosu&platform=pc&trid=20bd589e9ab64badb121dbf7cb657724&uipk=5&upsig=5f5d40bd4f4e4dd169948e2436132b9a&uparams=e,deadline,gen,nbs,oi,os,platform,trid,uipk&mid=37044417","backup_url":null},{"order":2,"length":518176,"size":126945591,"ahead":"","vhead":"","url":"http://upos-hz-mirrorcosu.acgvideo.com/upgcxcode/67/96/46919667/46919667-2-64.flv?e=ig8euxZM2rNcNbT1hwdVhoMjhWdVhwdEto8g5X10ugNcXBlqNxHxNEVE5XREto8KqJZHUa6m5J0SqE85tZvEuENvNC8xNEVE9EKE9IMvXBvE2ENvNCImNEVEK9GVqJIwqa80WXIekXRE9IMvXBvEuENvNCImNEVEua6m2jIxux0CkF6s2JZv5x0DQJZY2F8SkXKE9IB5QK==&deadline=1560489850&gen=playurl&nbs=1&oi=2026550635&os=cosu&platform=pc&trid=20bd589e9ab64badb121dbf7cb657724&uipk=5&upsig=894e5380a13d4f52257565ce8efe93fd&uparams=e,deadline,gen,nbs,oi,os,platform,trid,uipk&mid=37044417","backup_url":null},{"order":3,"length":358376,"size":94559280,"ahead":"","vhead":"","url":"http://upos-hz-mirrorks3u.acgvideo.com/upgcxcode/67/96/46919667/46919667-3-64.flv?e=ig8euxZM2rNcNbTB7zUVhoMj7WuBhwdEto8g5X10ugNcXBlqNxHxNEVE5XREto8KqJZHUa6m5J0SqE85tZvEuENvNC8xNEVE9EKE9IMvXBvE2ENvNCImNEVEK9GVqJIwqa80WXIekXRE9IMvXBvEuENvNCImNEVEua6m2jIxux0CkF6s2JZv5x0DQJZY2F8SkXKE9IB5QK==&deadline=1560489850&gen=playurl&nbs=1&oi=2026550635&os=ks3u&platform=pc&trid=20bd589e9ab64badb121dbf7cb657724&uipk=5&upsig=18c34e8c6ca85b54cc71d540e3fed014&uparams=e,deadline,gen,nbs,oi,os,platform,trid,uipk&mid=37044417","backup_url":["http://upos-hz-mirrorcosu.acgvideo.com/upgcxcode/67/96/46919667/46919667-3-64.flv?e=ig8euxZM2rNcNbTB7zUVhoMj7WuBhwdEto8g5X10ugNcXBlqNxHxNEVE5XREto8KqJZHUa6m5J0SqE85tZvEuENvNC8xNEVE9EKE9IMvXBvE2ENvNCImNEVEK9GVqJIwqa80WXIekXRE9IMvXBvEuENvNCImNEVEua6m2jIxux0CkF6s2JZv5x0DQJZY2F8SkXKE9IB5QK==&deadline=1560489850&gen=playurl&nbs=1&oi=2026550635&os=cosu&platform=pc&trid=20bd589e9ab64badb121dbf7cb657724&uipk=5&upsig=5c43f8bd5c584ae7a4ea2b6ca1b06488&uparams=e,deadline,gen,nbs,oi,os,platform,trid,uipk&mid=37044417"]},{"order":4,"length":187338,"size":50360127,"ahead":"","vhead":"","url":"http://upos-hz-mirrorks3u.acgvideo.com/upgcxcode/67/96/46919667/46919667-4-64.flv?e=ig8euxZM2rNcNbTahwdVhoMj7zdVhwdEto8g5X10ugNcXBlqNxHxNEVE5XREto8KqJZHUa6m5J0SqE85tZvEuENvNC8xNEVE9EKE9IMvXBvE2ENvNCImNEVEK9GVqJIwqa80WXIekXRE9IMvXBvEuENvNCImNEVEua6m2jIxux0CkF6s2JZv5x0DQJZY2F8SkXKE9IB5QK==&deadline=1560489850&gen=playurl&nbs=1&oi=2026550635&os=ks3u&platform=pc&trid=20bd589e9ab64badb121dbf7cb657724&uipk=5&upsig=38d440a717d6e002faa0a5b2aba5ad57&uparams=e,deadline,gen,nbs,oi,os,platform,trid,uipk&mid=37044417","backup_url":["http://upos-hz-mirrorcosu.acgvideo.com/upgcxcode/67/96/46919667/46919667-4-64.flv?e=ig8euxZM2rNcNbTahwdVhoMj7zdVhwdEto8g5X10ugNcXBlqNxHxNEVE5XREto8KqJZHUa6m5J0SqE85tZvEuENvNC8xNEVE9EKE9IMvXBvE2ENvNCImNEVEK9GVqJIwqa80WXIekXRE9IMvXBvEuENvNCImNEVEua6m2jIxux0CkF6s2JZv5x0DQJZY2F8SkXKE9IB5QK==&deadline=1560489850&gen=playurl&nbs=1&oi=2026550635&os=cosu&platform=pc&trid=20bd589e9ab64badb121dbf7cb657724&uipk=5&upsig=ffbeeedf2337ea68a7c1de53f62f4a52&uparams=e,deadline,gen,nbs,oi,os,platform,trid,uipk&mid=37044417"]},{"order":5,"length":479981,"size":104879669,"ahead":"","vhead":"","url":"http://upos-hz-mirrorcosu.acgvideo.com/upgcxcode/67/96/46919667/46919667-5-64.flv?e=ig8euxZM2rNcNbUM7WdVhoMB7wUVhwdEto8g5X10ugNcXBlqNxHxNEVE5XREto8KqJZHUa6m5J0SqE85tZvEuENvNC8xNEVE9EKE9IMvXBvE2ENvNCImNEVEK9GVqJIwqa80WXIekXRE9IMvXBvEuENvNCImNEVEua6m2jIxux0CkF6s2JZv5x0DQJZY2F8SkXKE9IB5QK==&deadline=1560489850&gen=playurl&nbs=1&oi=2026550635&os=cosu&platform=pc&trid=20bd589e9ab64badb121dbf7cb657724&uipk=5&upsig=755fb041a9012ab48c26e28427b5b3fc&uparams=e,deadline,gen,nbs,oi,os,platform,trid,uipk&mid=37044417","backup_url":null},{"order":6,"length":348861,"size":65235365,"ahead":"","vhead":"","url":"http://upos-hz-mirrorcosu.acgvideo.com/upgcxcode/67/96/46919667/46919667-6-64.flv?e=ig8euxZM2rNcNbKj7WdVhoMM7bUVhwdEto8g5X10ugNcXBlqNxHxNEVE5XREto8KqJZHUa6m5J0SqE85tZvEuENvNC8xNEVE9EKE9IMvXBvE2ENvNCImNEVEK9GVqJIwqa80WXIekXRE9IMvXBvEuENvNCImNEVEua6m2jIxux0CkF6s2JZv5x0DQJZY2F8SkXKE9IB5QK==&deadline=1560489850&gen=playurl&nbs=1&oi=2026550635&os=cosu&platform=pc&trid=20bd589e9ab64badb121dbf7cb657724&uipk=5&upsig=632933542126e0b17f3bc02918e27c99&uparams=e,deadline,gen,nbs,oi,os,platform,trid,uipk&mid=37044417","backup_url":null},{"order":7,"length":359983,"size":72860240,"ahead":"","vhead":"","url":"http://upos-hz-mirrorks3u.acgvideo.com/upgcxcode/67/96/46919667/46919667-7-64.flv?e=ig8euxZM2rNcNbUV7WdVhoMBhwUVhwdEto8g5X10ugNcXBlqNxHxNEVE5XREto8KqJZHUa6m5J0SqE85tZvEuENvNC8xNEVE9EKE9IMvXBvE2ENvNCImNEVEK9GVqJIwqa80WXIekXRE9IMvXBvEuENvNCImNEVEua6m2jIxux0CkF6s2JZv5x0DQJZY2F8SkXKE9IB5QK==&deadline=1560489850&gen=playurl&nbs=1&oi=2026550635&os=ks3u&platform=pc&trid=20bd589e9ab64badb121dbf7cb657724&uipk=5&upsig=bc6ea413c53178317e225d765ce0a70b&uparams=e,deadline,gen,nbs,oi,os,platform,trid,uipk&mid=37044417","backup_url":["http://upos-hz-mirrorcosu.acgvideo.com/upgcxcode/67/96/46919667/46919667-7-64.flv?e=ig8euxZM2rNcNbUV7WdVhoMBhwUVhwdEto8g5X10ugNcXBlqNxHxNEVE5XREto8KqJZHUa6m5J0SqE85tZvEuENvNC8xNEVE9EKE9IMvXBvE2ENvNCImNEVEK9GVqJIwqa80WXIekXRE9IMvXBvEuENvNCImNEVEua6m2jIxux0CkF6s2JZv5x0DQJZY2F8SkXKE9IB5QK==&deadline=1560489850&gen=playurl&nbs=1&oi=2026550635&os=cosu&platform=pc&trid=20bd589e9ab64badb121dbf7cb657724&uipk=5&upsig=174802df6c950147c57e35bd7f0affe6&uparams=e,deadline,gen,nbs,oi,os,platform,trid,uipk&mid=37044417"]},{"order":8,"length":289977,"size":49571696,"ahead":"","vhead":"","url":"http://upos-hz-mirrorkodou.acgvideo.com/upgcxcode/67/96/46919667/46919667-8-64.flv?e=ig8euxZM2rNcNbKg7WdVhoMMhbUVhwdEto8g5X10ugNcXBlqNxHxNEVE5XREto8KqJZHUa6m5J0SqE85tZvEuENvNC8xNEVE9EKE9IMvXBvE2ENvNCImNEVEK9GVqJIwqa80WXIekXRE9IMvXBvEuENvNCImNEVEua6m2jIxux0CkF6s2JZv5x0DQJZY2F8SkXKE9IB5QK==&deadline=1560489850&gen=playurl&nbs=1&oi=2026550635&os=kodou&platform=pc&trid=20bd589e9ab64badb121dbf7cb657724&uipk=5&upsig=6079f2531037f00abb28ec4838bd24ae&uparams=e,deadline,gen,nbs,oi,os,platform,trid,uipk&mid=37044417","backup_url":["http://upos-hz-mirrorcosu.acgvideo.com/upgcxcode/67/96/46919667/46919667-8-64.flv?e=ig8euxZM2rNcNbKg7WdVhoMMhbUVhwdEto8g5X10ugNcXBlqNxHxNEVE5XREto8KqJZHUa6m5J0SqE85tZvEuENvNC8xNEVE9EKE9IMvXBvE2ENvNCImNEVEK9GVqJIwqa80WXIekXRE9IMvXBvEuENvNCImNEVEua6m2jIxux0CkF6s2JZv5x0DQJZY2F8SkXKE9IB5QK==&deadline=1560489850&gen=playurl&nbs=1&oi=2026550635&os=cosu&platform=pc&trid=20bd589e9ab64badb121dbf7cb657724&uipk=5&upsig=b5dceb67f69aec9eef7423823b40faab&uparams=e,deadline,gen,nbs,oi,os,platform,trid,uipk&mid=37044417"]},{"order":9,"length":449539,"size":96911530,"ahead":"","vhead":"","url":"http://upos-hz-mirrorbosu.acgvideo.com/upgcxcode/67/96/46919667/46919667-9-64.flv?e=ig8euxZM2rNcNbUz7zUVhoMBhzuBhwdEto8g5X10ugNcXBlqNxHxNEVE5XREto8KqJZHUa6m5J0SqE85tZvEuENvNC8xNEVE9EKE9IMvXBvE2ENvNCImNEVEK9GVqJIwqa80WXIekXRE9IMvXBvEuENvNCImNEVEua6m2jIxux0CkF6s2JZv5x0DQJZY2F8SkXKE9IB5QK==&deadline=1560489850&gen=playurl&nbs=1&oi=2026550635&os=bosu&platform=pc&trid=20bd589e9ab64badb121dbf7cb657724&uipk=5&upsig=66de718210e0fe40518026e633768e77&uparams=e,deadline,gen,nbs,oi,os,platform,trid,uipk&mid=37044417","backup_url":["http://upos-hz-mirrorcosu.acgvideo.com/upgcxcode/67/96/46919667/46919667-9-64.flv?e=ig8euxZM2rNcNbUz7zUVhoMBhzuBhwdEto8g5X10ugNcXBlqNxHxNEVE5XREto8KqJZHUa6m5J0SqE85tZvEuENvNC8xNEVE9EKE9IMvXBvE2ENvNCImNEVEK9GVqJIwqa80WXIekXRE9IMvXBvEuENvNCImNEVEua6m2jIxux0CkF6s2JZv5x0DQJZY2F8SkXKE9IB5QK==&deadline=1560489850&gen=playurl&nbs=1&oi=2026550635&os=cosu&platform=pc&trid=20bd589e9ab64badb121dbf7cb657724&uipk=5&upsig=83917c9b77157fbc2ec36af4428cc717&uparams=e,deadline,gen,nbs,oi,os,platform,trid,uipk&mid=37044417"]},{"order":10,"length":363417,"size":121926516,"ahead":"","vhead":"","url":"http://upos-hz-mirrorcosu.acgvideo.com/upgcxcode/67/96/46919667/46919667-10-64.flv?e=ig8euxZM2rNcNb4z7zUVhoM3hzuBhwdEto8g5X10ugNcXBlqNxHxNEVE5XREto8KqJZHUa6m5J0SqE85tZvEuENvNC8xNEVE9EKE9IMvXBvE2ENvNCImNEVEK9GVqJIwqa80WXIekXRE9IMvXBvEuENvNCImNEVEua6m2jIxux0CkF6s2JZv5x0DQJZY2F8SkXKE9IB5QK==&deadline=1560489850&gen=playurl&nbs=1&oi=2026550635&os=cosu&platform=pc&trid=20bd589e9ab64badb121dbf7cb657724&uipk=5&upsig=fc2b504e12243397117bfe36f21b2006&uparams=e,deadline,gen,nbs,oi,os,platform,trid,uipk&mid=37044417","backup_url":null},{"order":11,"length":169160,"size":183816236,"ahead":"","vhead":"","url":"http://upos-hz-mirrorks3u.acgvideo.com/upgcxcode/67/96/46919667/46919667-11-64.flv?e=ig8euxZM2rNcNbNahWUVhwdlhbu17WdVhoNvNC8BqJIzNbfqXBvEqxTEto8BTrNvN0GvT90W5JZMkX_YN0MvXg8gNEVEuxTEto8i8o859r1qXg8xNEVE5XREto8GuFGv2U7SuxI72X6fTr859r1qXg8gNEVE5XREto8z5JZC2X2gkX5L5F1eTX1jkXlsTXHeux_f2o859IB_&deadline=1560489850&gen=playurl&nbs=1&oi=2026550635&os=ks3u&platform=pc&trid=20bd589e9ab64badb121dbf7cb657724&uipk=5&upsig=860791f68caf65e545931e85563e9c57&uparams=e,deadline,gen,nbs,oi,os,platform,trid,uipk&mid=37044417","backup_url":["http://upos-hz-mirrorcosu.acgvideo.com/upgcxcode/67/96/46919667/46919667-11-64.flv?e=ig8euxZM2rNcNbNahWUVhwdlhbu17WdVhoNvNC8BqJIzNbfqXBvEqxTEto8BTrNvN0GvT90W5JZMkX_YN0MvXg8gNEVEuxTEto8i8o859r1qXg8xNEVE5XREto8GuFGv2U7SuxI72X6fTr859r1qXg8gNEVE5XREto8z5JZC2X2gkX5L5F1eTX1jkXlsTXHeux_f2o859IB_&deadline=1560489850&gen=playurl&nbs=1&oi=2026550635&os=cosu&platform=pc&trid=20bd589e9ab64badb121dbf7cb657724&uipk=5&upsig=c5f283e9943afcf0809bfe66ea9cfb92&uparams=e,deadline,gen,nbs,oi,os,platform,trid,uipk&mid=37044417"]}]}
     * session : 92440a851106b26cab816b01bcdc0f6e
     * videoFrame : {}
     */

    private int code;
    private String message;
    private int ttl;
    private DataBean data;
    private String session;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    private transient int parts;

    public void setParts(int parts) {
        this.parts = parts;
    }

    @Override
    public int getParts() {
        return parts;
    }


    public static class DataBean {
        /**
         * from : local
         * result : suee
         * message :
         * quality : 64
         * format : flv720
         * timelength : 3786344
         * accept_format : flv,flv720,flv480,flv360
         * accept_description : ["高清 1080P","高清 720P","清晰 480P","流畅 360P"]
         * accept_quality : [80,64,32,16]
         * video_codecid : 7
         * seek_param : start
         * seek_type : offset
         * durl : [{"order":1,"length":261536,"size":63769493,"ahead":"EhA=","vhead":"AWQAH//hABpnZAAfrNlAUAW7ARAAAAMAEAAAAwMg8YMZYAEABWjr7PI8","url":"http://upos-hz-mirrorcosu.acgvideo.com/upgcxcode/67/96/46919667/46919667-1-64.flv?e=ig8euxZM2rNcNbTV7zUVhoMjhwuBhwdEto8g5X10ugNcXBlqNxHxNEVE5XREto8KqJZHUa6m5J0SqE85tZvEuENvNC8xNEVE9EKE9IMvXBvE2ENvNCImNEVEK9GVqJIwqa80WXIekXRE9IMvXBvEuENvNCImNEVEua6m2jIxux0CkF6s2JZv5x0DQJZY2F8SkXKE9IB5QK==&deadline=1560489850&gen=playurl&nbs=1&oi=2026550635&os=cosu&platform=pc&trid=20bd589e9ab64badb121dbf7cb657724&uipk=5&upsig=5f5d40bd4f4e4dd169948e2436132b9a&uparams=e,deadline,gen,nbs,oi,os,platform,trid,uipk&mid=37044417","backup_url":null},{"order":2,"length":518176,"size":126945591,"ahead":"","vhead":"","url":"http://upos-hz-mirrorcosu.acgvideo.com/upgcxcode/67/96/46919667/46919667-2-64.flv?e=ig8euxZM2rNcNbT1hwdVhoMjhWdVhwdEto8g5X10ugNcXBlqNxHxNEVE5XREto8KqJZHUa6m5J0SqE85tZvEuENvNC8xNEVE9EKE9IMvXBvE2ENvNCImNEVEK9GVqJIwqa80WXIekXRE9IMvXBvEuENvNCImNEVEua6m2jIxux0CkF6s2JZv5x0DQJZY2F8SkXKE9IB5QK==&deadline=1560489850&gen=playurl&nbs=1&oi=2026550635&os=cosu&platform=pc&trid=20bd589e9ab64badb121dbf7cb657724&uipk=5&upsig=894e5380a13d4f52257565ce8efe93fd&uparams=e,deadline,gen,nbs,oi,os,platform,trid,uipk&mid=37044417","backup_url":null},{"order":3,"length":358376,"size":94559280,"ahead":"","vhead":"","url":"http://upos-hz-mirrorks3u.acgvideo.com/upgcxcode/67/96/46919667/46919667-3-64.flv?e=ig8euxZM2rNcNbTB7zUVhoMj7WuBhwdEto8g5X10ugNcXBlqNxHxNEVE5XREto8KqJZHUa6m5J0SqE85tZvEuENvNC8xNEVE9EKE9IMvXBvE2ENvNCImNEVEK9GVqJIwqa80WXIekXRE9IMvXBvEuENvNCImNEVEua6m2jIxux0CkF6s2JZv5x0DQJZY2F8SkXKE9IB5QK==&deadline=1560489850&gen=playurl&nbs=1&oi=2026550635&os=ks3u&platform=pc&trid=20bd589e9ab64badb121dbf7cb657724&uipk=5&upsig=18c34e8c6ca85b54cc71d540e3fed014&uparams=e,deadline,gen,nbs,oi,os,platform,trid,uipk&mid=37044417","backup_url":["http://upos-hz-mirrorcosu.acgvideo.com/upgcxcode/67/96/46919667/46919667-3-64.flv?e=ig8euxZM2rNcNbTB7zUVhoMj7WuBhwdEto8g5X10ugNcXBlqNxHxNEVE5XREto8KqJZHUa6m5J0SqE85tZvEuENvNC8xNEVE9EKE9IMvXBvE2ENvNCImNEVEK9GVqJIwqa80WXIekXRE9IMvXBvEuENvNCImNEVEua6m2jIxux0CkF6s2JZv5x0DQJZY2F8SkXKE9IB5QK==&deadline=1560489850&gen=playurl&nbs=1&oi=2026550635&os=cosu&platform=pc&trid=20bd589e9ab64badb121dbf7cb657724&uipk=5&upsig=5c43f8bd5c584ae7a4ea2b6ca1b06488&uparams=e,deadline,gen,nbs,oi,os,platform,trid,uipk&mid=37044417"]},{"order":4,"length":187338,"size":50360127,"ahead":"","vhead":"","url":"http://upos-hz-mirrorks3u.acgvideo.com/upgcxcode/67/96/46919667/46919667-4-64.flv?e=ig8euxZM2rNcNbTahwdVhoMj7zdVhwdEto8g5X10ugNcXBlqNxHxNEVE5XREto8KqJZHUa6m5J0SqE85tZvEuENvNC8xNEVE9EKE9IMvXBvE2ENvNCImNEVEK9GVqJIwqa80WXIekXRE9IMvXBvEuENvNCImNEVEua6m2jIxux0CkF6s2JZv5x0DQJZY2F8SkXKE9IB5QK==&deadline=1560489850&gen=playurl&nbs=1&oi=2026550635&os=ks3u&platform=pc&trid=20bd589e9ab64badb121dbf7cb657724&uipk=5&upsig=38d440a717d6e002faa0a5b2aba5ad57&uparams=e,deadline,gen,nbs,oi,os,platform,trid,uipk&mid=37044417","backup_url":["http://upos-hz-mirrorcosu.acgvideo.com/upgcxcode/67/96/46919667/46919667-4-64.flv?e=ig8euxZM2rNcNbTahwdVhoMj7zdVhwdEto8g5X10ugNcXBlqNxHxNEVE5XREto8KqJZHUa6m5J0SqE85tZvEuENvNC8xNEVE9EKE9IMvXBvE2ENvNCImNEVEK9GVqJIwqa80WXIekXRE9IMvXBvEuENvNCImNEVEua6m2jIxux0CkF6s2JZv5x0DQJZY2F8SkXKE9IB5QK==&deadline=1560489850&gen=playurl&nbs=1&oi=2026550635&os=cosu&platform=pc&trid=20bd589e9ab64badb121dbf7cb657724&uipk=5&upsig=ffbeeedf2337ea68a7c1de53f62f4a52&uparams=e,deadline,gen,nbs,oi,os,platform,trid,uipk&mid=37044417"]},{"order":5,"length":479981,"size":104879669,"ahead":"","vhead":"","url":"http://upos-hz-mirrorcosu.acgvideo.com/upgcxcode/67/96/46919667/46919667-5-64.flv?e=ig8euxZM2rNcNbUM7WdVhoMB7wUVhwdEto8g5X10ugNcXBlqNxHxNEVE5XREto8KqJZHUa6m5J0SqE85tZvEuENvNC8xNEVE9EKE9IMvXBvE2ENvNCImNEVEK9GVqJIwqa80WXIekXRE9IMvXBvEuENvNCImNEVEua6m2jIxux0CkF6s2JZv5x0DQJZY2F8SkXKE9IB5QK==&deadline=1560489850&gen=playurl&nbs=1&oi=2026550635&os=cosu&platform=pc&trid=20bd589e9ab64badb121dbf7cb657724&uipk=5&upsig=755fb041a9012ab48c26e28427b5b3fc&uparams=e,deadline,gen,nbs,oi,os,platform,trid,uipk&mid=37044417","backup_url":null},{"order":6,"length":348861,"size":65235365,"ahead":"","vhead":"","url":"http://upos-hz-mirrorcosu.acgvideo.com/upgcxcode/67/96/46919667/46919667-6-64.flv?e=ig8euxZM2rNcNbKj7WdVhoMM7bUVhwdEto8g5X10ugNcXBlqNxHxNEVE5XREto8KqJZHUa6m5J0SqE85tZvEuENvNC8xNEVE9EKE9IMvXBvE2ENvNCImNEVEK9GVqJIwqa80WXIekXRE9IMvXBvEuENvNCImNEVEua6m2jIxux0CkF6s2JZv5x0DQJZY2F8SkXKE9IB5QK==&deadline=1560489850&gen=playurl&nbs=1&oi=2026550635&os=cosu&platform=pc&trid=20bd589e9ab64badb121dbf7cb657724&uipk=5&upsig=632933542126e0b17f3bc02918e27c99&uparams=e,deadline,gen,nbs,oi,os,platform,trid,uipk&mid=37044417","backup_url":null},{"order":7,"length":359983,"size":72860240,"ahead":"","vhead":"","url":"http://upos-hz-mirrorks3u.acgvideo.com/upgcxcode/67/96/46919667/46919667-7-64.flv?e=ig8euxZM2rNcNbUV7WdVhoMBhwUVhwdEto8g5X10ugNcXBlqNxHxNEVE5XREto8KqJZHUa6m5J0SqE85tZvEuENvNC8xNEVE9EKE9IMvXBvE2ENvNCImNEVEK9GVqJIwqa80WXIekXRE9IMvXBvEuENvNCImNEVEua6m2jIxux0CkF6s2JZv5x0DQJZY2F8SkXKE9IB5QK==&deadline=1560489850&gen=playurl&nbs=1&oi=2026550635&os=ks3u&platform=pc&trid=20bd589e9ab64badb121dbf7cb657724&uipk=5&upsig=bc6ea413c53178317e225d765ce0a70b&uparams=e,deadline,gen,nbs,oi,os,platform,trid,uipk&mid=37044417","backup_url":["http://upos-hz-mirrorcosu.acgvideo.com/upgcxcode/67/96/46919667/46919667-7-64.flv?e=ig8euxZM2rNcNbUV7WdVhoMBhwUVhwdEto8g5X10ugNcXBlqNxHxNEVE5XREto8KqJZHUa6m5J0SqE85tZvEuENvNC8xNEVE9EKE9IMvXBvE2ENvNCImNEVEK9GVqJIwqa80WXIekXRE9IMvXBvEuENvNCImNEVEua6m2jIxux0CkF6s2JZv5x0DQJZY2F8SkXKE9IB5QK==&deadline=1560489850&gen=playurl&nbs=1&oi=2026550635&os=cosu&platform=pc&trid=20bd589e9ab64badb121dbf7cb657724&uipk=5&upsig=174802df6c950147c57e35bd7f0affe6&uparams=e,deadline,gen,nbs,oi,os,platform,trid,uipk&mid=37044417"]},{"order":8,"length":289977,"size":49571696,"ahead":"","vhead":"","url":"http://upos-hz-mirrorkodou.acgvideo.com/upgcxcode/67/96/46919667/46919667-8-64.flv?e=ig8euxZM2rNcNbKg7WdVhoMMhbUVhwdEto8g5X10ugNcXBlqNxHxNEVE5XREto8KqJZHUa6m5J0SqE85tZvEuENvNC8xNEVE9EKE9IMvXBvE2ENvNCImNEVEK9GVqJIwqa80WXIekXRE9IMvXBvEuENvNCImNEVEua6m2jIxux0CkF6s2JZv5x0DQJZY2F8SkXKE9IB5QK==&deadline=1560489850&gen=playurl&nbs=1&oi=2026550635&os=kodou&platform=pc&trid=20bd589e9ab64badb121dbf7cb657724&uipk=5&upsig=6079f2531037f00abb28ec4838bd24ae&uparams=e,deadline,gen,nbs,oi,os,platform,trid,uipk&mid=37044417","backup_url":["http://upos-hz-mirrorcosu.acgvideo.com/upgcxcode/67/96/46919667/46919667-8-64.flv?e=ig8euxZM2rNcNbKg7WdVhoMMhbUVhwdEto8g5X10ugNcXBlqNxHxNEVE5XREto8KqJZHUa6m5J0SqE85tZvEuENvNC8xNEVE9EKE9IMvXBvE2ENvNCImNEVEK9GVqJIwqa80WXIekXRE9IMvXBvEuENvNCImNEVEua6m2jIxux0CkF6s2JZv5x0DQJZY2F8SkXKE9IB5QK==&deadline=1560489850&gen=playurl&nbs=1&oi=2026550635&os=cosu&platform=pc&trid=20bd589e9ab64badb121dbf7cb657724&uipk=5&upsig=b5dceb67f69aec9eef7423823b40faab&uparams=e,deadline,gen,nbs,oi,os,platform,trid,uipk&mid=37044417"]},{"order":9,"length":449539,"size":96911530,"ahead":"","vhead":"","url":"http://upos-hz-mirrorbosu.acgvideo.com/upgcxcode/67/96/46919667/46919667-9-64.flv?e=ig8euxZM2rNcNbUz7zUVhoMBhzuBhwdEto8g5X10ugNcXBlqNxHxNEVE5XREto8KqJZHUa6m5J0SqE85tZvEuENvNC8xNEVE9EKE9IMvXBvE2ENvNCImNEVEK9GVqJIwqa80WXIekXRE9IMvXBvEuENvNCImNEVEua6m2jIxux0CkF6s2JZv5x0DQJZY2F8SkXKE9IB5QK==&deadline=1560489850&gen=playurl&nbs=1&oi=2026550635&os=bosu&platform=pc&trid=20bd589e9ab64badb121dbf7cb657724&uipk=5&upsig=66de718210e0fe40518026e633768e77&uparams=e,deadline,gen,nbs,oi,os,platform,trid,uipk&mid=37044417","backup_url":["http://upos-hz-mirrorcosu.acgvideo.com/upgcxcode/67/96/46919667/46919667-9-64.flv?e=ig8euxZM2rNcNbUz7zUVhoMBhzuBhwdEto8g5X10ugNcXBlqNxHxNEVE5XREto8KqJZHUa6m5J0SqE85tZvEuENvNC8xNEVE9EKE9IMvXBvE2ENvNCImNEVEK9GVqJIwqa80WXIekXRE9IMvXBvEuENvNCImNEVEua6m2jIxux0CkF6s2JZv5x0DQJZY2F8SkXKE9IB5QK==&deadline=1560489850&gen=playurl&nbs=1&oi=2026550635&os=cosu&platform=pc&trid=20bd589e9ab64badb121dbf7cb657724&uipk=5&upsig=83917c9b77157fbc2ec36af4428cc717&uparams=e,deadline,gen,nbs,oi,os,platform,trid,uipk&mid=37044417"]},{"order":10,"length":363417,"size":121926516,"ahead":"","vhead":"","url":"http://upos-hz-mirrorcosu.acgvideo.com/upgcxcode/67/96/46919667/46919667-10-64.flv?e=ig8euxZM2rNcNb4z7zUVhoM3hzuBhwdEto8g5X10ugNcXBlqNxHxNEVE5XREto8KqJZHUa6m5J0SqE85tZvEuENvNC8xNEVE9EKE9IMvXBvE2ENvNCImNEVEK9GVqJIwqa80WXIekXRE9IMvXBvEuENvNCImNEVEua6m2jIxux0CkF6s2JZv5x0DQJZY2F8SkXKE9IB5QK==&deadline=1560489850&gen=playurl&nbs=1&oi=2026550635&os=cosu&platform=pc&trid=20bd589e9ab64badb121dbf7cb657724&uipk=5&upsig=fc2b504e12243397117bfe36f21b2006&uparams=e,deadline,gen,nbs,oi,os,platform,trid,uipk&mid=37044417","backup_url":null},{"order":11,"length":169160,"size":183816236,"ahead":"","vhead":"","url":"http://upos-hz-mirrorks3u.acgvideo.com/upgcxcode/67/96/46919667/46919667-11-64.flv?e=ig8euxZM2rNcNbNahWUVhwdlhbu17WdVhoNvNC8BqJIzNbfqXBvEqxTEto8BTrNvN0GvT90W5JZMkX_YN0MvXg8gNEVEuxTEto8i8o859r1qXg8xNEVE5XREto8GuFGv2U7SuxI72X6fTr859r1qXg8gNEVE5XREto8z5JZC2X2gkX5L5F1eTX1jkXlsTXHeux_f2o859IB_&deadline=1560489850&gen=playurl&nbs=1&oi=2026550635&os=ks3u&platform=pc&trid=20bd589e9ab64badb121dbf7cb657724&uipk=5&upsig=860791f68caf65e545931e85563e9c57&uparams=e,deadline,gen,nbs,oi,os,platform,trid,uipk&mid=37044417","backup_url":["http://upos-hz-mirrorcosu.acgvideo.com/upgcxcode/67/96/46919667/46919667-11-64.flv?e=ig8euxZM2rNcNbNahWUVhwdlhbu17WdVhoNvNC8BqJIzNbfqXBvEqxTEto8BTrNvN0GvT90W5JZMkX_YN0MvXg8gNEVEuxTEto8i8o859r1qXg8xNEVE5XREto8GuFGv2U7SuxI72X6fTr859r1qXg8gNEVE5XREto8z5JZC2X2gkX5L5F1eTX1jkXlsTXHeux_f2o859IB_&deadline=1560489850&gen=playurl&nbs=1&oi=2026550635&os=cosu&platform=pc&trid=20bd589e9ab64badb121dbf7cb657724&uipk=5&upsig=c5f283e9943afcf0809bfe66ea9cfb92&uparams=e,deadline,gen,nbs,oi,os,platform,trid,uipk&mid=37044417"]}]
         */

        private String from;
        private int quality;
        private String format;
        private int timelength;
        private String accept_format;
        private int video_codecid;
        private List<String> accept_description;
        private List<Integer> accept_quality;
        private List<DurlBean> durl;

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }


        public int getQuality() {
            return quality;
        }

        public void setQuality(int quality) {
            this.quality = quality;
        }

        public String getFormat() {
            return format;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        public int getTimelength() {
            return timelength;
        }

        public void setTimelength(int timelength) {
            this.timelength = timelength;
        }

        public String getAccept_format() {
            return accept_format;
        }

        public void setAccept_format(String accept_format) {
            this.accept_format = accept_format;
        }

        public int getVideo_codecid() {
            return video_codecid;
        }

        public void setVideo_codecid(int video_codecid) {
            this.video_codecid = video_codecid;
        }


        public List<String> getAccept_description() {
            return accept_description;
        }

        public void setAccept_description(List<String> accept_description) {
            this.accept_description = accept_description;
        }

        public List<Integer> getAccept_quality() {
            return accept_quality;
        }

        public void setAccept_quality(List<Integer> accept_quality) {
            this.accept_quality = accept_quality;
        }

        public List<DurlBean> getDurl() {
            return durl;
        }

        public void setDurl(List<DurlBean> durl) {
            this.durl = durl;
        }

        public static class DurlBean {
            /**
             * order : 1
             * length : 261536
             * size : 63769493
             * ahead : EhA=
             * vhead : AWQAH//hABpnZAAfrNlAUAW7ARAAAAMAEAAAAwMg8YMZYAEABWjr7PI8
             * url : http://upos-hz-mirrorcosu.acgvideo.com/upgcxcode/67/96/46919667/46919667-1-64.flv?e=ig8euxZM2rNcNbTV7zUVhoMjhwuBhwdEto8g5X10ugNcXBlqNxHxNEVE5XREto8KqJZHUa6m5J0SqE85tZvEuENvNC8xNEVE9EKE9IMvXBvE2ENvNCImNEVEK9GVqJIwqa80WXIekXRE9IMvXBvEuENvNCImNEVEua6m2jIxux0CkF6s2JZv5x0DQJZY2F8SkXKE9IB5QK==&deadline=1560489850&gen=playurl&nbs=1&oi=2026550635&os=cosu&platform=pc&trid=20bd589e9ab64badb121dbf7cb657724&uipk=5&upsig=5f5d40bd4f4e4dd169948e2436132b9a&uparams=e,deadline,gen,nbs,oi,os,platform,trid,uipk&mid=37044417
             * backup_url : null
             */

            private int order;
            private int length;
            private int size;
            private String ahead;
            private String vhead;
            private String url;
            private Object backup_url;

            public int getOrder() {
                return order;
            }

            public void setOrder(int order) {
                this.order = order;
            }

            public int getLength() {
                return length;
            }

            public void setLength(int length) {
                this.length = length;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }

            public String getAhead() {
                return ahead;
            }

            public void setAhead(String ahead) {
                this.ahead = ahead;
            }

            public String getVhead() {
                return vhead;
            }

            public void setVhead(String vhead) {
                this.vhead = vhead;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public Object getBackup_url() {
                return backup_url;
            }

            public void setBackup_url(Object backup_url) {
                this.backup_url = backup_url;
            }
        }
    }

}
