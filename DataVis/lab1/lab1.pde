// Рисует глобальную карту с указанием местоположения страны
// и отображением пузырьками уровень здоровья зубов в этой стране

PImage mapImage; // Фоновое изображение карты.
Table locationTable; // Таблица, в которой хранятся местоположения по странам.
Table dataTable; // Таблица, хранящая данные о состоянии зубов.
float dataMin = MAX_FLOAT;  // Используется для хранения минимальных                               
float dataMax = MIN_FLOAT;  // и максимальных значений данных.
float scale = 1.0;


float tx, ty;
float minScale, maxScale;
float xratio, yratio;

void setup()
{
  size(1400,800);
  
  // Загружаем фоновую карту.
  mapImage = loadImage("worldCountries.png");
  
  xratio = float(width) / mapImage.width;
  yratio = float(height) / mapImage.height;
  
  minScale = max(xratio, yratio);
  maxScale = 5;
  // Загружаем данныу из таблицы
  locationTable = loadTable("countryLocations.tsv","header,tsv");
  dataTable = loadTable("badTeeth.tsv","header,tsv");

  // Находим минимальное и максимальное значения "состояния" зубов
  for (int row=0; row<dataTable.getRowCount(); row++)
  {
    dataMin = min(dataMin,dataTable.getFloat(row,"NumBadTeeth"));
    dataMax = max(dataMax,dataTable.getFloat(row,"NumBadTeeth"));
  }
  
}

// Отображаем данные поверх фона.
void draw()
{
  float tw = mapImage.width / scale * xratio;
  float th = mapImage.height / scale * yratio;
  
  tx = constrain(tx, 0, mapImage.width-tw);
  ty = constrain(ty, 0, mapImage.height-th);
  pushMatrix();
  scale(scale);
  drawMap();
  popMatrix();
  beginShape(); 
 
  vertex(0, 0, tx, ty);
  vertex(width, 0, tx+tw, ty);
  vertex(width, height, tx+tw, ty+th);
  vertex(0, height, tx, ty+th);
  
  endShape();
}
void drawMap(){
  background(255);
  
  // Рисуем фоновое изображение, занимающее всю ширину и высоту окна.
  image(mapImage,0,0,width,height);

  // Устанавливаем внешний вид кругов данных.
  fill(192,0,0,80);
  stroke(255);
  strokeWeight(0.5);

  // Для каждой строки таблицы рисуем элемент данных в виде круга/пузырька.
  for (int row=0; row<dataTable.getRowCount(); row++)
  {
    // Получаем данные о плохих зубах по стране.
    String countryName = dataTable.getString(row,"CountryName");
    float badTeeth = dataTable.getFloat(row,"NumBadTeeth");
    float circleSize = map(badTeeth,dataMin,dataMax,1,25);

    // Ищем строку в таблице местоположения, которая соответствует стране.
    TableRow countryRow = locationTable.findRow(countryName,"CountryName");

    // Проверяем на наличие искомой страны в таблице со странами.
    if (countryRow != null)
    {
      // Берем широту и долготу из данных о стране.
      float latitude  = countryRow.getFloat("latitude");
      float longitude = countryRow.getFloat("longitude");

      // Масштабируем широту и долготу, чтобы они соответствовали метрике окна.
      float x = map(longitude,-180,180,0,width);
      float y = map(latitude,-60,85,height,0);
      ellipse(x,y,circleSize/scale*1.7,circleSize/scale*1.7);
    }
  }
}
// зум
void mouseWheel(MouseEvent event) {
  float zoomFactor = -event.getAmount()*.1 + 1; 
  float newScale = constrain(scale * zoomFactor, minScale, maxScale);
  
  tx -= (mouseX/newScale - mouseX/scale);
  ty -= (mouseY/newScale - mouseY/scale);

  scale = newScale;
}
