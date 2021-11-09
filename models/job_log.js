/* jshint indent: 2 */

module.exports = function(sequelize, DataTypes) {
  return sequelize.define('job_log', {
    'id': {
      type: DataTypes.INTEGER(11),
      allowNull: false,
      primaryKey: true,
      primaryKey: true,
      comment: "null",
      autoIncrement: true
    },
    'jobId': {
      type: DataTypes.INTEGER(11),
      allowNull: false,
      comment: "null",
      references: {
        model: 'job',
        key: 'id'
      }
    },
    'name': {
      type: DataTypes.STRING(45),
      allowNull: false,
      comment: "null"
    },
    'areaId': {
      type: DataTypes.INTEGER(11),
      allowNull: false,
      comment: "null",
      references: {
        model: 'area',
        key: 'id'
      }
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
    tableName: 'job_log',
    paranoid: true
  });
};
