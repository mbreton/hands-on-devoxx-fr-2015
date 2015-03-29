var casper = require('casper').create({
    logLevel: "fatal"              // Only "info" level messages will be logged
});

var baseUrl = 'http://localhost/~blemoine/typescript-devoxx/#/'

function nextSlide(id) {
    casper.wait(1000, function () {
        this.capture('print/slide-' + ("0" + id).slice(-2) + '.png', undefined, {
            format: 'png',
            quality: 100
        });
        var hasNextSlide = this.evaluate(function revealNext() {
            if (document.querySelector('.navigate-down').classList.contains('enabled')) {
                Reveal.down();
            } else if (document.querySelector('.navigate-right').classList.contains('enabled')){
                Reveal.next();
            } else{
                return false;
            }
            while (Reveal.nextFragment()) {
            }
            return true;
        });
        if (hasNextSlide) {
            nextSlide(id + 1);
        }
    });
};

casper.start(baseUrl, function () {
    this.viewport(1280, 720);
    nextSlide(0);
});

casper.run();
