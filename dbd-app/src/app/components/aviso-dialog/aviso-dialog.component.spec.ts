import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AvisoDialogComponent } from './aviso-dialog.component';

describe('AvisoDialogComponent', () => {
  let component: AvisoDialogComponent;
  let fixture: ComponentFixture<AvisoDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AvisoDialogComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AvisoDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
