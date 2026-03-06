/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.unicauca.piedrazul.infrastructure.microkernel.common;

import co.unicauca.piedrazul.infrastructure.microkernel.core.IReportPlugin;
import java.io.FileInputStream;
import java.util.Properties;
import java.io.IOException; 
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author santi
 */
public class ReportPluginManager {
    private static final String FILE_NAME = "plugin.properties";
    private static ReportPluginManager instance;
    
    private Properties pluginProperties; 
    
    private ReportPluginManager(){
        pluginProperties = new Properties();
    }
    public static ReportPluginManager getInstance(){
        return instance;
    }
    
    public static void init(String basePath) throws Exception{
        instance = new ReportPluginManager();
        instance.loadProperties(basePath);
        if(instance.pluginProperties.isEmpty()){
            throw new Exception("No se encontraron plugin para cargar");
        }
    }
    
    public IReportPlugin getReportPlugin (String reportCode){
         //Verificar si existe una entrada en el archivo para el país indicado.
        String propertyName = "report." + reportCode.toLowerCase();
        if(!pluginProperties.containsKey(propertyName)){
           return null;
        }
        IReportPlugin plugin = null;
        //Obtener el nombre de la clase del plugin.
        String pluginClassName = pluginProperties.getProperty(propertyName);
        try{
            //Obtenemos una referencia al tipo de la calse del plugin
            Class<?> pluginClass = Class.forName(pluginClassName);
            if(pluginClass != null){
                Object pluginObject = pluginClass.getDeclaredConstructor().newInstance(); // constructor por defecto de la clase o equivalente a new Clase()
                if(pluginObject instanceof IReportPlugin){
                    plugin = (IReportPlugin)pluginObject; //Casteo
                }
            }
        }catch(ClassNotFoundException | IllegalAccessException | IllegalArgumentException |
                InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException ex){
            Logger.getLogger("DeliveryPluginManager").log(Level.SEVERE, "Error al ejectura la aplicacion", ex);
        }
        return plugin;
    }
    private void loadProperties(String basePath){
        String filePath = basePath + FILE_NAME;
        try(FileInputStream stream = new FileInputStream(filePath)){
            pluginProperties.load(stream);
        }catch(IOException ex){
            Logger.getLogger("ReportPluginMananger").log(Level.SEVERE, "Error al cargar el directorio del plugin", ex);
        }
    }
}
