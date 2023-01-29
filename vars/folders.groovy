@Grab('org.yaml:snakeyaml:1.33')

import org.yaml.snakeyaml.Yaml


def call() {

  def folders = new Yaml().load(libraryResource('seed_jobs/folders.yaml'))

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
