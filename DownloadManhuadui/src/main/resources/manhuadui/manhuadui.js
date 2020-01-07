function decrypt20180904() {
    var key = CryptoJS.enc.Utf8.parse("123456781234567G");  //十六位字符作为密钥
    var iv = CryptoJS.enc.Utf8.parse('ABCDEF1G34123412');
    var decrypt = CryptoJS.AES.decrypt(chapterImages,key, { iv: iv, mode: CryptoJS.mode.CBC, padding: CryptoJS.pad.Pkcs7 });
    var decryptedStr = decrypt.toString(CryptoJS.enc.Utf8);
    return decryptedStr.toString();
}
String.prototype.trim = function (charlist) {
    charlist = charlist || ' \t\n\r\x0B';
    return this.replace(new RegExp('(^[' + charlist + ']+)|([' + charlist + ']+$)', 'g'), '');
};

String.prototype.ltrim = function (charlist) {
    charlist = charlist || ' \t\n\r\x0B';
    return this.replace(new RegExp('^[' + charlist + ']+', 'g'), '');
};

function getChapterImage(arr,page) {
    var filename = arr[page - 1];
    var host = "https://www.manhuadui.com";
    if (filename === undefined) return host + '/images/default/common.png';
    if (filename.match(/^https?:\/\/(images.dmzj.com|imgsmall.dmzj.com)/i)) return 'https://mhimg.eshanyao.com/showImage.php?url='+encodeURI(filename);
    if (filename.match(/^[a-z]\//i)) return 'https://mhimg.eshanyao.com/showImage.php?url='+encodeURI("https://images.dmzj.com/"+filename);
    if (filename.match(/^(http:|https:|ftp:|^)\/\//i)) return filename;
    filename =chapterPath.trim('/') + '/' + filename.ltrim('/');
    return host + '/' + filename.ltrim('/');
}

