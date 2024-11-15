package sample.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class TransportModel {
    ArrayList<Transport> transportsList = new ArrayList<>();//список с данными
    private int counter = 1;//счетчик
    Class<? extends Transport> transportFilter = Transport.class;

    public interface DataChangedListener {
        void dataChanged(ArrayList<Transport> transportsList);
    }

    private ArrayList<DataChangedListener> dataChangedListeners = new ArrayList<>();

    public void addDataChangedListener(DataChangedListener listener) {
        this.dataChangedListeners.add(listener);
    }

    public void load() {
        transportsList.clear();
        this.add(new Aircraft("Airbus 322", 800, 3400, 3750, 73), true);
        this.add(new Train("Паровозик Томас", 99, 666, 13), true);
        this.add(new Aircraft("Airbus 122", 750, 3400, 3750, 75), true);
        this.emitDataChanged();
    }

    public void add(Transport transport) {
        add(transport, true);
    }

    public void add(Transport transport, boolean emit) {
        transport.id = counter; // присваиваем id, значение счетчика
        counter++; // увеличиваем счетчик на единицу
        this.transportsList.add(transport);
        if (emit) {
            this.emitDataChanged();
        }
    }

    public void delete(int id) {
        for (int i = 0; i < this.transportsList.size(); ++i) {
            // ищем в цикле еду с данным айдишником
            if (this.transportsList.get(i).id == id) {
                // если нашли то удаляем
                this.transportsList.remove(i);
                break;
            }
        }
        this.emitDataChanged();
    }

    private void emitDataChanged() {
        for (DataChangedListener listener : dataChangedListeners) {
            ArrayList<Transport> filteredList = new ArrayList<>(
                    transportsList.stream() // запускаем стрим
                            .filter(transport -> transportFilter.isInstance(transport)) // фильтруем по типу
                            .collect(Collectors.toList()) // превращаем в список
            );
            listener.dataChanged(filteredList); // подсовывам сюда список
        }
    }


    public void edit(Transport transport) {
        // ищем объект в списке
        for (int i = 0; i < this.transportsList.size(); ++i) {
            // чтобы id совпадал с id переданным форме
            if (this.transportsList.get(i).id == transport.id) {
                // если нашли, то подменяем объект
                this.transportsList.set(i, transport);
                break;
            }
        }
        this.emitDataChanged();
    }

    public void saveToFile(String path) {
        try (Writer writer = new FileWriter(path)) {
            // создаем сериализатор
            ObjectMapper mapper = new ObjectMapper();
            // записываем данные списка list в файл
            mapper.writerFor(new TypeReference<ArrayList<Transport>>() {
            }) // указали какой тип
                    .withDefaultPrettyPrinter()
                    .writeValue(writer, transportsList); // тут запись
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile(String path) {
        try (Reader reader = new FileReader(path)) {
            ObjectMapper mapper = new ObjectMapper();
            transportsList = mapper.readerFor(new TypeReference<ArrayList<Transport>>() {
            }).readValue(reader);
            // рассчитываем счетчик как максимальное значение id плюс 1
            this.counter = transportsList.stream()
                    .map(transport -> transport.id)
                    .max(Integer::compareTo)
                    .orElse(0) + 1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.emitDataChanged();
    }

    public void setTransportFilter(Class<? extends Transport> transportFilter) {
        this.transportFilter = transportFilter;
        this.emitDataChanged();
    }

    public int calcRoad(String type) {
        Integer road = 0;
        if (type == "all") {
            for (int i = 0; i < transportsList.size(); i++) {
                road += transportsList.get(i).getDistance();
            }
        } else if (type == "train") {
            for (int i = 0; i < transportsList.size(); i++) {
                if (transportsList.get(i) instanceof Train) {
                    road += transportsList.get(i).getDistance();
                }
            }
        } else if (type == "aircraft") {
            for (int i = 0; i < transportsList.size(); i++) {
                if (transportsList.get(i) instanceof Aircraft) {
                    road += transportsList.get(i).getDistance();
                }
            }
        }
        return road;
    }
}
