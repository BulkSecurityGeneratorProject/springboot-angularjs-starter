function mockApiAccountCall() {
    inject(function($httpBackend) {
        $httpBackend.whenGET(/rest\/account.*/).respond({});
    });
}

function mockI18nCalls() {
    inject(function($httpBackend) {
        $httpBackend.whenGET(/i18n\/[a-z][a-z]\/.+\.json/).respond({});
    });
}
