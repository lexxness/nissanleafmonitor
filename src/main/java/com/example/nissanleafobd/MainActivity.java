package com.example.nissanleafobd;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.github.pires.obd.commands.ObdCommand;
import com.github.pires.obd.commands.protocol.ObdProtocolCommand;
import com.github.pires.obd.commands.temperature.TemperatureCommand;
import com.github.pires.obd.exceptions.ObdCommandException;
import com.github.pires.obd.obdCommand;
import com.github.pires.obd.obdCommand;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private TextView voltageTextView;
    private TextView cellVoltageTextView;
    private TextView chargePercentageTextView;
    private TextView climateSettingsTextView;

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice bluetoothDevice;
    private OBD2 obd2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        voltageTextView = findViewById(R.id.voltageTextView);
        cellVoltageTextView = findViewById(R.id.cellVoltageTextView);
        chargePercentageTextView = findViewById(R.id.chargePercentageTextView);
        climateSettingsTextView = findViewById(R.id.climateSettingsTextView);
        Button settingsButton = findViewById(R.id.settingsButton);
        ImageView batteryIcon = findViewById(R.id.batteryIcon);

        // Инициализация Bluetooth и OBD-II
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        connectToOBD();

        batteryIcon.setImageResource(R.drawable.ic_battery); // Замените на ваш ресурс

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectOBDAdapter();
            }
        });
    }

    private void connectToOBD(String deviceAddress) {
        // Подключение к OBD-II
        bluetoothDevice = bluetoothAdapter.getRemoteDevice(deviceAddress);
        obd2 = new OBD2(bluetoothDevice);
        try {
            obd2.connect();
            updateData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateData() {
        // Получение данных о напряжении и климате
        try {
            String voltage = getVoltage();
            voltageTextView.setText("Общее напряжение: " + voltage);
            String cellVoltage = getCellVoltage();
            cellVoltageTextView.setText("Напряжение по ячейкам: " + cellVoltage);
            String chargePercentage = getChargePercentage();
            chargePercentageTextView.setText("Процент заряда: " + chargePercentage);
            String climateSettings = getClimateSettings();
            climateSettingsTextView.setText("Настройки климата: " + climateSettings);
        } catch (ObdCommandException e) {
            e.printStackTrace();
        }
    }

    private String getVoltage() throws ObdCommandException {
        // Логика получения общего напряжения
        ObdCommand command = new ObdProtocolCommand("010C"); // Пример команды
        command.run(obd2);
        return command.getFormattedResult();
    }

    private String getCellVoltage() throws ObdCommandException {
        // Логика получения напряжения по ячейкам
        ObdCommand command = new ObdProtocolCommand("010B"); // Пример команды для получения напряжения
        command.run(obd2);
        return command.getFormattedResult();
    }

    private String getChargePercentage() throws ObdCommandException {
        // Логика расчета процента заряда
        ObdCommand command = new ObdProtocolCommand("012F"); // Пример команды для получения процента заряда
        command.run(obd2);
        return command.getFormattedResult();
    }

    private String getClimateSettings() throws ObdCommandException {
        // Логика получения настроек климата
        ObdCommand command = new ObdProtocolCommand("0130"); // Пример команды для получения настроек климата
        command.run(obd2);
        return command.getFormattedResult();
    }

    private void selectOBDAdapter() {
        // Получение списка спаренных Bluetooth-устройств
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            String[] deviceNames = new String[pairedDevices.size()];
            final String[] deviceAddresses = new String[pairedDevices.size()];
            int index = 0;
            for (BluetoothDevice device : pairedDevices) {
                deviceNames[index] = device.getName();
                deviceAddresses[index] = device.getAddress();
                index++;
            }

            // Показать диалог выбора устройства
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Выберите OBD-II адаптер")
                    .setItems(deviceNames, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            connectToOBD(deviceAddresses[which]);
                        }
                    });
            builder.create().show();
        } else {
            Toast.makeText(this, "Нет доступных OBD-II адаптеров", Toast.LENGTH_SHORT).show();
        }
    }
}
