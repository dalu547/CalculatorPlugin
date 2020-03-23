var exec = require('cordova/exec');

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
