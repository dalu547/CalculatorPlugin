var exec = require('cordova/exec');
 
    function Calculator() 
    {
        this.format = {
        "all_1D": 61918,
        "aztec": 1,
        "codabar": 2,
        "code_128": 16,
        "code_39": 4,
        "code_93": 8,
        "data_MATRIX": 32,
        "ean_13": 128,
        "ean_8": 64,
        "itf": 256,
        "maxicode": 512,
        "msi": 131072,
        "pdf_417": 1024,
        "plessey": 262144,
        "qr_CODE": 2048,
        "rss_14": 4096,
        "rss_EXPANDED": 8192,
        "upc_A": 16384,
        "upc_E": 32768,
        "upc_EAN_EXTENSION": 65536
        };
    }

module.exports.coolMethod = function (arg0, success, error) {
    exec(success, error, 'Calculator', 'coolMethod', [arg0]);
};

module.exports.add = function (arg0, success, error) {
    exec(success, error, 'Calculator', 'add', [arg0]);
};

module.exports.multiply = function (arg0, success, error) {
    exec(success, error, 'Calculator', 'multiply', [arg0]);
};

module.exports.substract = function (arg0, success, error) {
    exec(success, error, 'Calculator', 'substract', [arg0]);
};

module.exports.divide = function (arg0, success, error) {
    exec(success, error, 'Calculator', 'divide', [arg0]);
};

module.exports.flashOnOff = function (arg0, success, error) {
    exec(success, error, 'Calculator', 'flashOnOff', [arg0]);
};

module.exports.scan = function (arg0, success, error) {
    exec(success, error, 'Calculator', 'scan', [arg0]);
};




