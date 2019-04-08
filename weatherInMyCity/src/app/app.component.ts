import { Component } from '@angular/core';
import { OpenWeatherModel } from './open-weather-data-model';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { FormGroup, FormControl } from '@angular/forms';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.sass']
})
export class AppComponent {
  title = 'weatherInMyCity';
  openWeatherDataModel: OpenWeatherModel;
  searchForm = new FormGroup({
    city: new FormControl('')
  });

  constructor(private httpClient: HttpClient) {
  }

  showWeather() {
    let url = `http://api.openweathermap.org/data/2.5/weather?q=${this.searchForm.value.city}&APPID=6942c0db2d3c74dee8bac19903717863`;
    this.httpClient.get<OpenWeatherModel>(url).subscribe(data => {    
      this.openWeatherDataModel = data.weather[0];      
    })
  }
}
