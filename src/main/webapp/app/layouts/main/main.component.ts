import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import FooterComponent from '../footer/footer.component';
import PageRibbonComponent from '../profiles/page-ribbon.component';
import { SidebarComponent } from '../sidebar/sidebar.component';

@Component({
  selector: 'jhi-main',
  templateUrl: './main.component.html',
  standalone: true,
  imports: [RouterOutlet, FooterComponent, PageRibbonComponent, SidebarComponent],
})
export default class MainComponent {}
