import { NgModule } from '@angular/core';

import { MyapplicationSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [MyapplicationSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [MyapplicationSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class MyapplicationSharedCommonModule {}
