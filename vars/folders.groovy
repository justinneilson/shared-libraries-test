@Grab('org.yaml:snakeyaml:1.33')

import org.yaml.snakeyaml.Yaml


def call() {

  String folderConfig = libraryResource('seed_jobs/folders.yaml')

  def folders = new Yaml((folderConfig as File).text).load()

  folders.each { f ->
    folder(f.name) {
      description(f.description)
      displayName(f.displayName)
      primaryView(f.primaryView)
      // Add the authorization config provided in the yaml file
      authorization {
        configure { auth ->
          auth / 'permission' << f.authorization
        }
      }

      configure { folder ->
        def healthMetricsNode = folder / 'healthMetrics'
        healthMetricsNode.children().replaceNode{ }
      }
      
      // configure the properties specified in the yaml file
      properties {
        configure { props ->
          props / 'hudson.model.ParametersDefinitionProperty' << f.properties
        }
      }
    }
  }
}
