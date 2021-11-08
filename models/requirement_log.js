/* jshint indent: 2 */

module.exports = function(sequelize, DataTypes) {
  return sequelize.define('requirement_log', {
    'id': {
      type: DataTypes.INTEGER,
      allowNull: false,
      primaryKey: true,
      primaryKey: true,
      comment: "null",
      autoIncrement: true
    },
    'requirementId': {
      type: DataTypes.INTEGER,
      allowNull: false,
      comment: "null"
    },
    'name': {
      type: DataTypes.STRING(45),
      allowNull: false,
      comment: "null"
    },
    'projectId': {
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
    tableName: 'requirement_log',
    paranoid: true
  });
};
