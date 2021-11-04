/* jshint indent: 2 */

module.exports = function(sequelize, DataTypes) {
  return sequelize.define('project', {
    'id': {
      type: DataTypes.INTEGER,
      allowNull: false,
      primaryKey: true,
      primaryKey: true,
      comment: "null",
      autoIncrement: true
    },
    'name': {
      type: DataTypes.STRING(45),
      allowNull: false,
      comment: "null"
    },
    'clientId': {
      type: DataTypes.INTEGER,
      allowNull: false,
      comment: "null",
      references: {
        model: 'client',
        key: 'id'
      }
    },
    'managerId': {
      type: DataTypes.INTEGER,
      allowNull: false,
      comment: "null",
      references: {
        model: 'member',
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
    tableName: 'project',
    paranoid: true
  });
};
