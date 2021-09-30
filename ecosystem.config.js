module.exports = {
  apps : [{
    name: 'Solera Jobs',
    script: 'server.js',
    instances: 'max',
    max_memory_restart: '256M',
    env: {
      NODE_ENV: 'development'
    },
    env_production: {
      NODE_ENV: 'production'
    }
  }]
};
