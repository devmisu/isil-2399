/* jshint indent: 2 */

module.exports = function(sequelize, DataTypes) {
  return sequelize.define('project_log', {
    'id': {
      type: DataTypes.INTEGER,
      allowNull: false,
      primaryKey: true,
      primaryKey: true,
      comment: "null",
      autoIncrement: true
    },
    'projectId': {
      type: DataTypes.INTEGER,
      allowNull: false,
      comment: "null"
    },
    'name': {
      type: DataTypes.STRING(45),
      allowNull: false,
      comment: "null"
    },
    'clientId': {
      type: DataTypes.INTEGER,
      allowNull: false,
      comment: "null"
    },
    'managerId': {
      type: DataTypes.INTEGER,
      allowNull: false,
      comment: "null"
    },
    'createdAt': {
      type: DataTypes.DATE,
      allowNull: false,
      comment: "null"
    },
    'updatedAt': {
      type: DataTypes.DATE,
      allowNull: true,
      comment: "null"
    },
    'deletedAt': {
      type: DataTypes.DATE,
      allowNull: true,
      comment: "null"
    }
  }, {
    tableName: 'project_log',
    paranoid: true
  });
};
